package com.ivanov

class SimpleTagLib {
    static defaultEncodeAs = [taglib:'html']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]
    def dateFormat = { attrs, body ->
        out << new java.text.SimpleDateFormat(attrs.format).format(attrs.date)
    }
}
