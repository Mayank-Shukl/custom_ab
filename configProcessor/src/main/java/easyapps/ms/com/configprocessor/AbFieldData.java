package easyapps.ms.com.configprocessor;

import javax.lang.model.element.Element;

final class AbFieldData {
    final String fieldName;
    final Element element;

    AbFieldData(String fieldName, Element element) {
        this.fieldName = fieldName;
        this.element = element;
    }
}
