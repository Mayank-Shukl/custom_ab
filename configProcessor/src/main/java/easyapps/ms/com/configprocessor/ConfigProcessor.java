package easyapps.ms.com.configprocessor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import easyapps.ms.com.config_annotation.AbElement;
import easyapps.ms.com.config_annotation.ConfigClass;
import easyapps.ms.com.config_annotation.ConfigContainer;
import easyapps.ms.com.config_annotation.ConfigProvider;

@SupportedAnnotationTypes({
        "easyapps.ms.com.config_annotation",
        "easyapps.ms.com.config_annotation.ConfigClass",
        "easyapps.ms.com.config_annotation.ConfigContainer",
        "easyapps.ms.com.config_annotation.ConfigProvider"

})
public final class ConfigProcessor extends AbstractProcessor {

    private static final String PACKAGE_NAME = "com.easyapps";
    private static final String FIELD_MAP_CONFIG_VALUES = "mapResults";
    private static final String CLASS_NAME_AB_MANAGER = "AbManager";
    private static final String METHOD_PREFIX_GET_AB_FIELD = "get";
    private static final String METHOD_PREFIX_BOOLEAN_AB_FIELD = "is";
    private static final String INTENT_CLASS = "android.content.Intent";
    private static final String ACTIVITY_TYPE = "android.app.Activity";
    private static final String LOCALMAP_CLASS = "easyapps.ms.com.config_annotation.utils.LocalMap";

    private final List<MethodSpec> newGettersMethodSpecs = new ArrayList<>();

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        EnvironmentUtil.init(processingEnvironment);
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        EnvironmentUtil.logInfo("Round ab annotations");
        try {
            if (canProcessAnnotations(roundEnvironment)) {
                createAbManagerClass();
            }
            newGettersMethodSpecs.clear();
        } catch (IOException e) {
            EnvironmentUtil.logError("error processing ab annoations");
        }
        return true;
    }


    private void createNavigatorClass(TypeElement className, TypeElement container) throws IOException {

        TypeName intentType = ClassName.bestGuess(INTENT_CLASS);
        TypeName activityType = ClassName.bestGuess(ACTIVITY_TYPE);


        MethodSpec.Builder method = MethodSpec.methodBuilder("launchCustomizer")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addStatement("$T data =$T." + getProvider(container), className, container)
                .addParameter(activityType, "activity")
                .addStatement("$T intent = new $T($S)", intentType, intentType, "android.action.config_launcher")
                .addStatement("intent.putExtra($S,data)", "CONFIG")
                .addStatement("activity.startActivity(intent)")
                .returns(TypeName.VOID);

        final TypeSpec.Builder builder = TypeSpec.classBuilder("Navigator");
        builder.addModifiers(Modifier.PUBLIC)
                .addMethod(method.build());

        EnvironmentUtil.logInfo("Navigator class generated");
        EnvironmentUtil.generateFile(builder.build(), PACKAGE_NAME);
    }

    private boolean canProcessAnnotations(RoundEnvironment roundEnv) throws IOException {
        return processConfigClasses(roundEnv);
    }

    private boolean processConfigClasses(RoundEnvironment roundEnv) throws IOException {
        EnvironmentUtil.logInfo("Processing config class");
        final Set<? extends Element> configContainer = roundEnv.getElementsAnnotatedWith(ConfigContainer.class);
        final Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(ConfigClass.class);

        if (Utils.isNullOrEmpty(elements) && Utils.isNullOrEmpty(configContainer)) {
            EnvironmentUtil.logInfo("No more configs to process");
            return false;
        }
        TypeElement container = null;
        for (Element provider : configContainer) {
            if (provider.getKind() != ElementKind.CLASS) {
                EnvironmentUtil.logError("Provider can only be used for Methods!");
                return false;
            }
            // expecting only one provider
            container = (TypeElement) provider;
        }

        for (Element element : elements) {
            if (element.getKind() != ElementKind.CLASS) {
                EnvironmentUtil.logError("ConfigClass can only be used for classes!");
                return false;
            }
            createNavigatorClass((TypeElement) element, container);
            generateConfigGetters((TypeElement) element, container);
        }
        return true;
    }

    private String getProvider(TypeElement container) {
        final List<? extends Element> citizens = container.getEnclosedElements();
        if (Utils.isNullOrEmpty(citizens)) return null;

        for (Element citizen : citizens) {
            final ConfigProvider configProvider = citizen.getAnnotation(ConfigProvider.class);
            if (configProvider != null) {
                EnvironmentUtil.logInfo("config provider Method " + citizen.getSimpleName());
                Set<Modifier> modifiers = citizen.getModifiers();
                if (modifiers.contains(Modifier.STATIC)) {
                    return citizen.getSimpleName() + "()";
                } else {
                    return "getInstance()." + citizen.getSimpleName() + "()";
                }
            }
        }
        return null;
    }

    private boolean generateConfigGetters(TypeElement parent, TypeElement container) {
        EnvironmentUtil.logInfo("generating getters for " + parent.getSimpleName());
        final List<AbFieldData> pairs = getAbElementFields(parent);
        final String providerPrefix = getProvider(container);
        if (!Utils.isNullOrEmpty(pairs)) {
            TypeName className = ClassName.bestGuess(container.getQualifiedName().toString());
            for (AbFieldData data : pairs) {

                Element element = data.element;
                final TypeMirror typeMirror = data.element.asType();
                final TypeName typeName = ClassName.get(typeMirror);

                final String prefix = getPrefixString(typeName);

                String methodName = prefix
                        + element.getSimpleName().toString().substring(0, 1).toUpperCase()
                        + element.getSimpleName().toString().substring(1);

                final MethodSpec.Builder getterMethodBuilder = MethodSpec
                        .methodBuilder(methodName)
                        .addModifiers(Modifier.PUBLIC)
                        .returns(typeName);

                getterMethodBuilder
                        .beginControlFlow("if ($L != null && $L.containsKey($S))", FIELD_MAP_CONFIG_VALUES,
                                FIELD_MAP_CONFIG_VALUES, data.element.getSimpleName())
                        .addStatement("return ($T)$L.get($S)", typeName, FIELD_MAP_CONFIG_VALUES,
                                data.element.getSimpleName())
                        .endControlFlow();

                getterMethodBuilder.addStatement("return $T." + providerPrefix + "." + methodName + "()", className);

                newGettersMethodSpecs.add(getterMethodBuilder.build());
            }
        }
        return true;
    }


    // here we havent checked for boxed boolean (Boolean) as lombok generates get for the function
    private String getPrefixString(TypeName typeName) {
        String prefix = METHOD_PREFIX_GET_AB_FIELD;
        if (TypeName.BOOLEAN.equals(typeName)) {
            prefix = METHOD_PREFIX_BOOLEAN_AB_FIELD;
        }
        return prefix;
    }

    private List<AbFieldData> getAbElementFields(Element parent) {
        final List<? extends Element> citizens = parent.getEnclosedElements();
        if (Utils.isNullOrEmpty(citizens)) return null;

        final List<AbFieldData> abFieldData = new ArrayList<>();
        for (Element citizen : citizens) {
            final AbElement abAnnotation = citizen.getAnnotation(AbElement.class);
            if (abAnnotation != null) {
                EnvironmentUtil.logInfo("Field name " + citizen.getSimpleName());
                abFieldData.add(new AbFieldData(citizen.getSimpleName().toString(), citizen));
            }
        }

        return abFieldData;
    }

    private void createAbManagerClass() throws IOException {
        TypeName wildcard = ClassName.get(Object.class);
        TypeName string = ClassName.get(String.class);

        TypeName mapOfStringAndObject = ParameterizedTypeName.get(ClassName.get(Map.class), string, wildcard);

        TypeName className = ClassName.bestGuess(LOCALMAP_CLASS);
        CodeBlock codeBlock = CodeBlock.builder().addStatement("$T.getLocalMap()", className).build();

        FieldSpec fieldSpec = FieldSpec.builder(mapOfStringAndObject, FIELD_MAP_CONFIG_VALUES)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .initializer(codeBlock)
                .build();

        final TypeSpec.Builder builder = TypeSpec.classBuilder(CLASS_NAME_AB_MANAGER);
        builder.addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                .addField(fieldSpec);

        for (MethodSpec methodSpec : newGettersMethodSpecs) {
            builder.addMethod(methodSpec);
        }

        EnvironmentUtil.logInfo("Ab class generated");
        EnvironmentUtil.generateFile(builder.build(), PACKAGE_NAME);
    }
}
