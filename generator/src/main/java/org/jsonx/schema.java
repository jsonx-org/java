package org.jsonx;

@java.lang.SuppressWarnings("all")
public class schema {
  public abstract static class Any extends org.jsonx.schema.Member {
    @org.jsonx.StringProperty(name="jx:type", pattern="any", nullable=false)
    public java.lang.String getJx3aType() {
      return jx3aType;
    }

    public void setJx3aType(final java.lang.String jx3aType) {
      this.jx3aType = jx3aType;
    }

    private java.lang.String jx3aType;

    @org.jsonx.StringProperty(name="types", pattern="\\S+( \\S+)*", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getTypes() {
      return types;
    }

    public void setTypes(final java.lang.String types) {
      this.types = types;
    }

    private java.lang.String types;

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.Any) || !super.equals(obj))
        return false;

      final org.jsonx.schema.Any that = (org.jsonx.schema.Any)obj;
      if (!org.libj.lang.ObjectUtil.equals(jx3aType, that.jx3aType))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(types, that.types))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 188459569 * 31 + super.hashCode();
      if (jx3aType != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(jx3aType);

      if (types != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(types);

      return hashCode;
    }
  }

  public static class AnyElement extends org.jsonx.schema.Any {
    @org.jsonx.BooleanProperty(name="nullable", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.Boolean getNullable() {
      return nullable;
    }

    public void setNullable(final java.lang.Boolean nullable) {
      this.nullable = nullable;
    }

    private java.lang.Boolean nullable;

    @org.jsonx.StringProperty(name="minOccurs", pattern="[\\d]+", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getMinOccurs() {
      return minOccurs;
    }

    public void setMinOccurs(final java.lang.String minOccurs) {
      this.minOccurs = minOccurs;
    }

    private java.lang.String minOccurs;

    @org.jsonx.StringProperty(name="maxOccurs", pattern="([\\d]+|unbounded)", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getMaxOccurs() {
      return maxOccurs;
    }

    public void setMaxOccurs(final java.lang.String maxOccurs) {
      this.maxOccurs = maxOccurs;
    }

    private java.lang.String maxOccurs;

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.AnyElement) || !super.equals(obj))
        return false;

      final org.jsonx.schema.AnyElement that = (org.jsonx.schema.AnyElement)obj;
      if (!org.libj.lang.ObjectUtil.equals(nullable, that.nullable))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(minOccurs, that.minOccurs))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(maxOccurs, that.maxOccurs))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 1449550731 * 31 + super.hashCode();
      if (nullable != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(nullable);

      if (minOccurs != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(minOccurs);

      if (maxOccurs != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(maxOccurs);

      return hashCode;
    }
  }

  public static class AnyProperty extends org.jsonx.schema.Any {
    @org.jsonx.BooleanProperty(name="nullable", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.Boolean getNullable() {
      return nullable;
    }

    public void setNullable(final java.lang.Boolean nullable) {
      this.nullable = nullable;
    }

    private java.lang.Boolean nullable;

    @org.jsonx.StringProperty(name="use", pattern="(required|optional)", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getUse() {
      return use;
    }

    public void setUse(final java.lang.String use) {
      this.use = use;
    }

    private java.lang.String use;

    @org.jsonx.ArrayProperty(name="bindings", type=org.jsonx.schema.FieldBindings.class, use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.util.List<org.jsonx.schema.FieldBinding> getBindings() {
      return bindings;
    }

    public void setBindings(final java.util.List<org.jsonx.schema.FieldBinding> bindings) {
      this.bindings = bindings;
    }

    private java.util.List<org.jsonx.schema.FieldBinding> bindings;

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.AnyProperty) || !super.equals(obj))
        return false;

      final org.jsonx.schema.AnyProperty that = (org.jsonx.schema.AnyProperty)obj;
      if (!org.libj.lang.ObjectUtil.equals(nullable, that.nullable))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(use, that.use))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(bindings, that.bindings))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 1001597734 * 31 + super.hashCode();
      if (nullable != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(nullable);

      if (use != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(use);

      if (bindings != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(bindings);

      return hashCode;
    }
  }

  public static class Array extends org.jsonx.schema.Member {
    @org.jsonx.StringProperty(name="jx:type", pattern="array", nullable=false)
    public java.lang.String getJx3aType() {
      return jx3aType;
    }

    public void setJx3aType(final java.lang.String jx3aType) {
      this.jx3aType = jx3aType;
    }

    private java.lang.String jx3aType;

    /** Specifies the minimum inclusive number of iterations of child elements. Default: 1. **/
    @org.jsonx.StringProperty(name="minIterate", pattern="[\\d]+", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getMinIterate() {
      return minIterate;
    }

    /** Specifies the minimum inclusive number of iterations of child elements. Default: 1. **/
    public void setMinIterate(final java.lang.String minIterate) {
      this.minIterate = minIterate;
    }

    private java.lang.String minIterate;

    /** Specifies the maximum inclusive number of iterations of child elements. Default: 1. **/
    @org.jsonx.StringProperty(name="maxIterate", pattern="([\\d]+|unbounded)", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getMaxIterate() {
      return maxIterate;
    }

    /** Specifies the maximum inclusive number of iterations of child elements. Default: 1. **/
    public void setMaxIterate(final java.lang.String maxIterate) {
      this.maxIterate = maxIterate;
    }

    private java.lang.String maxIterate;

    @org.jsonx.ObjectElement(id=6, type=org.jsonx.schema.ObjectElement.class, minOccurs=0, nullable=false)
    @org.jsonx.ObjectElement(id=5, type=org.jsonx.schema.StringElement.class, minOccurs=0, nullable=false)
    @org.jsonx.ObjectElement(id=4, type=org.jsonx.schema.ReferenceElement.class, minOccurs=0, nullable=false)
    @org.jsonx.ObjectElement(id=3, type=org.jsonx.schema.NumberElement.class, minOccurs=0, nullable=false)
    @org.jsonx.ObjectElement(id=2, type=org.jsonx.schema.BooleanElement.class, minOccurs=0, nullable=false)
    @org.jsonx.ObjectElement(id=1, type=org.jsonx.schema.ArrayElement.class, minOccurs=0, nullable=false)
    @org.jsonx.ObjectElement(id=0, type=org.jsonx.schema.AnyElement.class, minOccurs=0, nullable=false)
    @org.jsonx.ArrayProperty(name="elements", elementIds={0, 1, 2, 3, 4, 5, 6}, maxIterate=2147483647, nullable=false)
    public java.util.List<org.jsonx.schema.Member> getElements() {
      return elements;
    }

    public void setElements(final java.util.List<org.jsonx.schema.Member> elements) {
      this.elements = elements;
    }

    private java.util.List<org.jsonx.schema.Member> elements;

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.Array) || !super.equals(obj))
        return false;

      final org.jsonx.schema.Array that = (org.jsonx.schema.Array)obj;
      if (!org.libj.lang.ObjectUtil.equals(jx3aType, that.jx3aType))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(minIterate, that.minIterate))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(maxIterate, that.maxIterate))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(elements, that.elements))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 721134942 * 31 + super.hashCode();
      if (jx3aType != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(jx3aType);

      if (minIterate != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(minIterate);

      if (maxIterate != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(maxIterate);

      if (elements != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(elements);

      return hashCode;
    }
  }

  public static class ArrayElement extends org.jsonx.schema.Array {
    @org.jsonx.BooleanProperty(name="nullable", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.Boolean getNullable() {
      return nullable;
    }

    public void setNullable(final java.lang.Boolean nullable) {
      this.nullable = nullable;
    }

    private java.lang.Boolean nullable;

    @org.jsonx.StringProperty(name="minOccurs", pattern="[\\d]+", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getMinOccurs() {
      return minOccurs;
    }

    public void setMinOccurs(final java.lang.String minOccurs) {
      this.minOccurs = minOccurs;
    }

    private java.lang.String minOccurs;

    @org.jsonx.StringProperty(name="maxOccurs", pattern="([\\d]+|unbounded)", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getMaxOccurs() {
      return maxOccurs;
    }

    public void setMaxOccurs(final java.lang.String maxOccurs) {
      this.maxOccurs = maxOccurs;
    }

    private java.lang.String maxOccurs;

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.ArrayElement) || !super.equals(obj))
        return false;

      final org.jsonx.schema.ArrayElement that = (org.jsonx.schema.ArrayElement)obj;
      if (!org.libj.lang.ObjectUtil.equals(nullable, that.nullable))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(minOccurs, that.minOccurs))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(maxOccurs, that.maxOccurs))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = -614798402 * 31 + super.hashCode();
      if (nullable != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(nullable);

      if (minOccurs != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(minOccurs);

      if (maxOccurs != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(maxOccurs);

      return hashCode;
    }
  }

  public static class ArrayProperty extends org.jsonx.schema.Array {
    @org.jsonx.BooleanProperty(name="nullable", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.Boolean getNullable() {
      return nullable;
    }

    public void setNullable(final java.lang.Boolean nullable) {
      this.nullable = nullable;
    }

    private java.lang.Boolean nullable;

    @org.jsonx.StringProperty(name="use", pattern="(required|optional)", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getUse() {
      return use;
    }

    public void setUse(final java.lang.String use) {
      this.use = use;
    }

    private java.lang.String use;

    @org.jsonx.ArrayProperty(name="bindings", type=org.jsonx.schema.FieldBindings.class, use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.util.List<org.jsonx.schema.FieldBinding> getBindings() {
      return bindings;
    }

    public void setBindings(final java.util.List<org.jsonx.schema.FieldBinding> bindings) {
      this.bindings = bindings;
    }

    private java.util.List<org.jsonx.schema.FieldBinding> bindings;

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.ArrayProperty) || !super.equals(obj))
        return false;

      final org.jsonx.schema.ArrayProperty that = (org.jsonx.schema.ArrayProperty)obj;
      if (!org.libj.lang.ObjectUtil.equals(nullable, that.nullable))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(use, that.use))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(bindings, that.bindings))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 1431284051 * 31 + super.hashCode();
      if (nullable != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(nullable);

      if (use != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(use);

      if (bindings != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(bindings);

      return hashCode;
    }
  }

  public abstract static class Binding implements org.jsonx.JxObject {
    /** Specifies the language to which this binding applies. **/
    @org.jsonx.StringProperty(name="lang", pattern="\\S+( \\S+)*", nullable=false)
    public java.lang.String getLang() {
      return lang;
    }

    /** Specifies the language to which this binding applies. **/
    public void setLang(final java.lang.String lang) {
      this.lang = lang;
    }

    private java.lang.String lang;

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.Binding))
        return false;

      final org.jsonx.schema.Binding that = (org.jsonx.schema.Binding)obj;
      if (!org.libj.lang.ObjectUtil.equals(lang, that.lang))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 2147169354;
      if (lang != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(lang);

      return hashCode;
    }

    @java.lang.Override
    public java.lang.String toString() {
      return org.jsonx.JxEncoder.get().marshal(this);
    }
  }

  public static class Boolean extends org.jsonx.schema.Member {
    @org.jsonx.StringProperty(name="jx:type", pattern="boolean", nullable=false)
    public java.lang.String getJx3aType() {
      return jx3aType;
    }

    public void setJx3aType(final java.lang.String jx3aType) {
      this.jx3aType = jx3aType;
    }

    private java.lang.String jx3aType;

    @org.jsonx.ArrayProperty(name="bindings", type=org.jsonx.schema.TypeBindings.class, use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.util.List<org.jsonx.schema.TypeBinding> getBindings() {
      return bindings;
    }

    public void setBindings(final java.util.List<org.jsonx.schema.TypeBinding> bindings) {
      this.bindings = bindings;
    }

    private java.util.List<org.jsonx.schema.TypeBinding> bindings;

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.Boolean) || !super.equals(obj))
        return false;

      final org.jsonx.schema.Boolean that = (org.jsonx.schema.Boolean)obj;
      if (!org.libj.lang.ObjectUtil.equals(jx3aType, that.jx3aType))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(bindings, that.bindings))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = -1974865427 * 31 + super.hashCode();
      if (jx3aType != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(jx3aType);

      if (bindings != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(bindings);

      return hashCode;
    }
  }

  public static class BooleanElement extends org.jsonx.schema.Boolean {
    @org.jsonx.BooleanProperty(name="nullable", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.Boolean getNullable() {
      return nullable;
    }

    public void setNullable(final java.lang.Boolean nullable) {
      this.nullable = nullable;
    }

    private java.lang.Boolean nullable;

    @org.jsonx.StringProperty(name="minOccurs", pattern="[\\d]+", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getMinOccurs() {
      return minOccurs;
    }

    public void setMinOccurs(final java.lang.String minOccurs) {
      this.minOccurs = minOccurs;
    }

    private java.lang.String minOccurs;

    @org.jsonx.StringProperty(name="maxOccurs", pattern="([\\d]+|unbounded)", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getMaxOccurs() {
      return maxOccurs;
    }

    public void setMaxOccurs(final java.lang.String maxOccurs) {
      this.maxOccurs = maxOccurs;
    }

    private java.lang.String maxOccurs;

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.BooleanElement) || !super.equals(obj))
        return false;

      final org.jsonx.schema.BooleanElement that = (org.jsonx.schema.BooleanElement)obj;
      if (!org.libj.lang.ObjectUtil.equals(nullable, that.nullable))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(minOccurs, that.minOccurs))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(maxOccurs, that.maxOccurs))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 677851215 * 31 + super.hashCode();
      if (nullable != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(nullable);

      if (minOccurs != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(minOccurs);

      if (maxOccurs != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(maxOccurs);

      return hashCode;
    }
  }

  public static class BooleanProperty extends org.jsonx.schema.Boolean {
    @org.jsonx.BooleanProperty(name="nullable", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.Boolean getNullable() {
      return nullable;
    }

    public void setNullable(final java.lang.Boolean nullable) {
      this.nullable = nullable;
    }

    private java.lang.Boolean nullable;

    @org.jsonx.StringProperty(name="use", pattern="(required|optional)", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getUse() {
      return use;
    }

    public void setUse(final java.lang.String use) {
      this.use = use;
    }

    private java.lang.String use;

    @org.jsonx.ArrayProperty(name="bindings", type=org.jsonx.schema.TypeFieldBindings.class, use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.util.List<org.jsonx.schema.TypeBinding> getBindings() {
      return (java.util.List<org.jsonx.schema.TypeBinding>)super.getBindings();
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.BooleanProperty) || !super.equals(obj))
        return false;

      final org.jsonx.schema.BooleanProperty that = (org.jsonx.schema.BooleanProperty)obj;
      if (!org.libj.lang.ObjectUtil.equals(nullable, that.nullable))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(use, that.use))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = -1446250782 * 31 + super.hashCode();
      if (nullable != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(nullable);

      if (use != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(use);

      return hashCode;
    }
  }

  public abstract static class Documented implements org.jsonx.JxObject {
    /** Defines text comments. Optional. **/
    @org.jsonx.StringProperty(name="doc")
    public java.lang.String getDoc() {
      return doc;
    }

    /** Defines text comments. Optional. **/
    public void setDoc(final java.lang.String doc) {
      this.doc = doc;
    }

    private java.lang.String doc;

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.Documented))
        return false;

      final org.jsonx.schema.Documented that = (org.jsonx.schema.Documented)obj;
      if (!org.libj.lang.ObjectUtil.equals(doc, that.doc))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = -955389163;
      if (doc != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(doc);

      return hashCode;
    }

    @java.lang.Override
    public java.lang.String toString() {
      return org.jsonx.JxEncoder.get().marshal(this);
    }
  }

  /** Specifies language-specific binding. **/
  public static class FieldBinding extends org.jsonx.schema.Binding {
    /** Specifies the "field" identifier. **/
    @org.jsonx.StringProperty(name="field", pattern="[a-zA-Z_$][a-zA-Z\\d_$]*", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getField() {
      return field;
    }

    /** Specifies the "field" identifier. **/
    public void setField(final java.lang.String field) {
      this.field = field;
    }

    private java.lang.String field;

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.FieldBinding) || !super.equals(obj))
        return false;

      final org.jsonx.schema.FieldBinding that = (org.jsonx.schema.FieldBinding)obj;
      if (!org.libj.lang.ObjectUtil.equals(field, that.field))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = -1808808570 * 31 + super.hashCode();
      if (field != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(field);

      return hashCode;
    }
  }

  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  @org.jsonx.ObjectElement(id=0, type=org.jsonx.schema.FieldBinding.class, nullable=false)
  @org.jsonx.ArrayType(elementIds={0})
  /** Specifies language-specific bindings. **/
  public static @interface FieldBindings {
  }

  public abstract static class Member extends org.jsonx.schema.Documented {
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.Member) || !super.equals(obj))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      return 1211742261 * 31 + super.hashCode();
    }
  }

  public static class Number extends org.jsonx.schema.Member {
    @org.jsonx.StringProperty(name="jx:type", pattern="number", nullable=false)
    public java.lang.String getJx3aType() {
      return jx3aType;
    }

    public void setJx3aType(final java.lang.String jx3aType) {
      this.jx3aType = jx3aType;
    }

    private java.lang.String jx3aType;

    /**        Specifies the value range in interval notation:
           Open (exclusive) interval: (min,max)
           Closed (inclusive) interval: [min,max]
           Half-open or half-closed interval: [min,max)
           Degenerate interval: [val] **/
    @org.jsonx.StringProperty(name="range", pattern="[\\(\\[](-?(0|[1-9]\\d*)(\\.\\d+)?([eE][+-]?([1-9]\\d*))?)?,(-?(0|[1-9]\\d*)(\\.\\d+)?([eE][+-]?([1-9]\\d*))?)?[\\)\\]]", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getRange() {
      return range;
    }

    /**        Specifies the value range in interval notation:
           Open (exclusive) interval: (min,max)
           Closed (inclusive) interval: [min,max]
           Half-open or half-closed interval: [min,max)
           Degenerate interval: [val] **/
    public void setRange(final java.lang.String range) {
      this.range = range;
    }

    private java.lang.String range;

    /** The number of digits to the right of the decimal point. If a value is not specified, the scale is unbounded. **/
    @org.jsonx.NumberProperty(name="scale", scale=0, use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.math.BigInteger getScale() {
      return scale;
    }

    /** The number of digits to the right of the decimal point. If a value is not specified, the scale is unbounded. **/
    public void setScale(final java.math.BigInteger scale) {
      this.scale = scale;
    }

    private java.math.BigInteger scale;

    @org.jsonx.ArrayProperty(name="bindings", type=org.jsonx.schema.TypeBindings.class, use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.util.List<org.jsonx.schema.TypeBinding> getBindings() {
      return bindings;
    }

    public void setBindings(final java.util.List<org.jsonx.schema.TypeBinding> bindings) {
      this.bindings = bindings;
    }

    private java.util.List<org.jsonx.schema.TypeBinding> bindings;

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.Number) || !super.equals(obj))
        return false;

      final org.jsonx.schema.Number that = (org.jsonx.schema.Number)obj;
      if (!org.libj.lang.ObjectUtil.equals(jx3aType, that.jx3aType))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(range, that.range))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(scale, that.scale))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(bindings, that.bindings))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 1255147748 * 31 + super.hashCode();
      if (jx3aType != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(jx3aType);

      if (range != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(range);

      if (scale != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(scale);

      if (bindings != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(bindings);

      return hashCode;
    }
  }

  public static class NumberElement extends org.jsonx.schema.Number {
    @org.jsonx.BooleanProperty(name="nullable", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.Boolean getNullable() {
      return nullable;
    }

    public void setNullable(final java.lang.Boolean nullable) {
      this.nullable = nullable;
    }

    private java.lang.Boolean nullable;

    @org.jsonx.StringProperty(name="minOccurs", pattern="[\\d]+", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getMinOccurs() {
      return minOccurs;
    }

    public void setMinOccurs(final java.lang.String minOccurs) {
      this.minOccurs = minOccurs;
    }

    private java.lang.String minOccurs;

    @org.jsonx.StringProperty(name="maxOccurs", pattern="([\\d]+|unbounded)", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getMaxOccurs() {
      return maxOccurs;
    }

    public void setMaxOccurs(final java.lang.String maxOccurs) {
      this.maxOccurs = maxOccurs;
    }

    private java.lang.String maxOccurs;

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.NumberElement) || !super.equals(obj))
        return false;

      final org.jsonx.schema.NumberElement that = (org.jsonx.schema.NumberElement)obj;
      if (!org.libj.lang.ObjectUtil.equals(nullable, that.nullable))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(minOccurs, that.minOccurs))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(maxOccurs, that.maxOccurs))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 2034148728 * 31 + super.hashCode();
      if (nullable != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(nullable);

      if (minOccurs != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(minOccurs);

      if (maxOccurs != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(maxOccurs);

      return hashCode;
    }
  }

  public static class NumberProperty extends org.jsonx.schema.Number {
    @org.jsonx.BooleanProperty(name="nullable", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.Boolean getNullable() {
      return nullable;
    }

    public void setNullable(final java.lang.Boolean nullable) {
      this.nullable = nullable;
    }

    private java.lang.Boolean nullable;

    @org.jsonx.StringProperty(name="use", pattern="(required|optional)", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getUse() {
      return use;
    }

    public void setUse(final java.lang.String use) {
      this.use = use;
    }

    private java.lang.String use;

    @org.jsonx.ArrayProperty(name="bindings", type=org.jsonx.schema.TypeFieldBindings.class, use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.util.List<org.jsonx.schema.TypeBinding> getBindings() {
      return (java.util.List<org.jsonx.schema.TypeBinding>)super.getBindings();
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.NumberProperty) || !super.equals(obj))
        return false;

      final org.jsonx.schema.NumberProperty that = (org.jsonx.schema.NumberProperty)obj;
      if (!org.libj.lang.ObjectUtil.equals(nullable, that.nullable))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(use, that.use))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 1944266457 * 31 + super.hashCode();
      if (nullable != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(nullable);

      if (use != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(use);

      return hashCode;
    }
  }

  public abstract static class Object extends org.jsonx.schema.Member {
    public static class Properties implements org.jsonx.JxObject {
      @org.jsonx.AnyProperty(name=".*", types={@org.jsonx.t(objects=org.jsonx.schema.AnyProperty.class), @org.jsonx.t(objects=org.jsonx.schema.ArrayProperty.class), @org.jsonx.t(objects=org.jsonx.schema.BooleanProperty.class), @org.jsonx.t(objects=org.jsonx.schema.NumberProperty.class), @org.jsonx.t(objects=org.jsonx.schema.ObjectProperty.class), @org.jsonx.t(objects=org.jsonx.schema.ReferenceProperty.class), @org.jsonx.t(objects=org.jsonx.schema.StringProperty.class)}, nullable=false)
      public java.util.LinkedHashMap<java.lang.String,? extends org.jsonx.schema.Member> getProperties() {
        return properties;
      }

      public void setProperties(final java.util.LinkedHashMap<java.lang.String,? extends org.jsonx.schema.Member> properties) {
        this.properties = properties;
      }

      private java.util.LinkedHashMap<java.lang.String,? extends org.jsonx.schema.Member> properties;

      @java.lang.Override
      public boolean equals(final java.lang.Object obj) {
        if (obj == this)
          return true;

        if (!(obj instanceof org.jsonx.schema.Object.Properties))
          return false;

        final org.jsonx.schema.Object.Properties that = (org.jsonx.schema.Object.Properties)obj;
        if (!org.libj.lang.ObjectUtil.equals(properties, that.properties))
          return false;

        return true;
      }

      @java.lang.Override
      public int hashCode() {
        int hashCode = -946499747;
        if (properties != null)
          hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(properties);

        return hashCode;
      }

      @java.lang.Override
      public java.lang.String toString() {
        return org.jsonx.JxEncoder.get().marshal(this);
      }
    }

    @org.jsonx.StringProperty(name="jx:type", pattern="object", nullable=false)
    public java.lang.String getJx3aType() {
      return jx3aType;
    }

    public void setJx3aType(final java.lang.String jx3aType) {
      this.jx3aType = jx3aType;
    }

    private java.lang.String jx3aType;

    /** Specifies the name of the type to extend. Optional. **/
    @org.jsonx.StringProperty(name="extends", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getExtends() {
      return _extends;
    }

    /** Specifies the name of the type to extend. Optional. **/
    public void setExtends(final java.lang.String _extends) {
      this._extends = _extends;
    }

    private java.lang.String _extends;

    @org.jsonx.ObjectProperty(name="properties", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public org.jsonx.schema.Object.Properties getProperties() {
      return properties;
    }

    public void setProperties(final org.jsonx.schema.Object.Properties properties) {
      this.properties = properties;
    }

    private org.jsonx.schema.Object.Properties properties;

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.Object) || !super.equals(obj))
        return false;

      final org.jsonx.schema.Object that = (org.jsonx.schema.Object)obj;
      if (!org.libj.lang.ObjectUtil.equals(jx3aType, that.jx3aType))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(_extends, that._extends))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(properties, that.properties))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 1266143450 * 31 + super.hashCode();
      if (jx3aType != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(jx3aType);

      if (_extends != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_extends);

      if (properties != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(properties);

      return hashCode;
    }
  }

  public static class ObjectElement extends org.jsonx.schema.Object {
    @org.jsonx.BooleanProperty(name="nullable", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.Boolean getNullable() {
      return nullable;
    }

    public void setNullable(final java.lang.Boolean nullable) {
      this.nullable = nullable;
    }

    private java.lang.Boolean nullable;

    @org.jsonx.StringProperty(name="minOccurs", pattern="[\\d]+", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getMinOccurs() {
      return minOccurs;
    }

    public void setMinOccurs(final java.lang.String minOccurs) {
      this.minOccurs = minOccurs;
    }

    private java.lang.String minOccurs;

    @org.jsonx.StringProperty(name="maxOccurs", pattern="([\\d]+|unbounded)", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getMaxOccurs() {
      return maxOccurs;
    }

    public void setMaxOccurs(final java.lang.String maxOccurs) {
      this.maxOccurs = maxOccurs;
    }

    private java.lang.String maxOccurs;

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.ObjectElement) || !super.equals(obj))
        return false;

      final org.jsonx.schema.ObjectElement that = (org.jsonx.schema.ObjectElement)obj;
      if (!org.libj.lang.ObjectUtil.equals(nullable, that.nullable))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(minOccurs, that.minOccurs))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(maxOccurs, that.maxOccurs))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = -1694884670 * 31 + super.hashCode();
      if (nullable != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(nullable);

      if (minOccurs != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(minOccurs);

      if (maxOccurs != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(maxOccurs);

      return hashCode;
    }
  }

  public static class ObjectProperty extends org.jsonx.schema.Object {
    @org.jsonx.BooleanProperty(name="nullable", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.Boolean getNullable() {
      return nullable;
    }

    public void setNullable(final java.lang.Boolean nullable) {
      this.nullable = nullable;
    }

    private java.lang.Boolean nullable;

    @org.jsonx.StringProperty(name="use", pattern="(required|optional)", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getUse() {
      return use;
    }

    public void setUse(final java.lang.String use) {
      this.use = use;
    }

    private java.lang.String use;

    @org.jsonx.ArrayProperty(name="bindings", type=org.jsonx.schema.FieldBindings.class, use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.util.List<org.jsonx.schema.FieldBinding> getBindings() {
      return bindings;
    }

    public void setBindings(final java.util.List<org.jsonx.schema.FieldBinding> bindings) {
      this.bindings = bindings;
    }

    private java.util.List<org.jsonx.schema.FieldBinding> bindings;

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.ObjectProperty) || !super.equals(obj))
        return false;

      final org.jsonx.schema.ObjectProperty that = (org.jsonx.schema.ObjectProperty)obj;
      if (!org.libj.lang.ObjectUtil.equals(nullable, that.nullable))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(use, that.use))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(bindings, that.bindings))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = -1986619185 * 31 + super.hashCode();
      if (nullable != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(nullable);

      if (use != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(use);

      if (bindings != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(bindings);

      return hashCode;
    }
  }

  public static class ObjectType extends org.jsonx.schema.Object {
    /** Specifies whether the object is abstract. Default: false. **/
    @org.jsonx.BooleanProperty(name="abstract", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.Boolean getAbstract() {
      return _abstract;
    }

    /** Specifies whether the object is abstract. Default: false. **/
    public void setAbstract(final java.lang.Boolean _abstract) {
      this._abstract = _abstract;
    }

    private java.lang.Boolean _abstract;

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.ObjectType) || !super.equals(obj))
        return false;

      final org.jsonx.schema.ObjectType that = (org.jsonx.schema.ObjectType)obj;
      if (!org.libj.lang.ObjectUtil.equals(_abstract, that._abstract))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 926406452 * 31 + super.hashCode();
      if (_abstract != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_abstract);

      return hashCode;
    }
  }

  public abstract static class Reference extends org.jsonx.schema.Member {
    @org.jsonx.StringProperty(name="jx:type", pattern="reference", nullable=false)
    public java.lang.String getJx3aType() {
      return jx3aType;
    }

    public void setJx3aType(final java.lang.String jx3aType) {
      this.jx3aType = jx3aType;
    }

    private java.lang.String jx3aType;

    /** Specifies the name of the referenced type. Required. **/
    @org.jsonx.StringProperty(name="type", pattern="\\S+( \\S+)*", nullable=false)
    public java.lang.String getType() {
      return type;
    }

    /** Specifies the name of the referenced type. Required. **/
    public void setType(final java.lang.String type) {
      this.type = type;
    }

    private java.lang.String type;

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.Reference) || !super.equals(obj))
        return false;

      final org.jsonx.schema.Reference that = (org.jsonx.schema.Reference)obj;
      if (!org.libj.lang.ObjectUtil.equals(jx3aType, that.jx3aType))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(type, that.type))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 1841260496 * 31 + super.hashCode();
      if (jx3aType != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(jx3aType);

      if (type != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(type);

      return hashCode;
    }
  }

  public static class ReferenceElement extends org.jsonx.schema.Reference {
    @org.jsonx.BooleanProperty(name="nullable", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.Boolean getNullable() {
      return nullable;
    }

    public void setNullable(final java.lang.Boolean nullable) {
      this.nullable = nullable;
    }

    private java.lang.Boolean nullable;

    @org.jsonx.StringProperty(name="minOccurs", pattern="[\\d]+", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getMinOccurs() {
      return minOccurs;
    }

    public void setMinOccurs(final java.lang.String minOccurs) {
      this.minOccurs = minOccurs;
    }

    private java.lang.String minOccurs;

    @org.jsonx.StringProperty(name="maxOccurs", pattern="([\\d]+|unbounded)", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getMaxOccurs() {
      return maxOccurs;
    }

    public void setMaxOccurs(final java.lang.String maxOccurs) {
      this.maxOccurs = maxOccurs;
    }

    private java.lang.String maxOccurs;

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.ReferenceElement) || !super.equals(obj))
        return false;

      final org.jsonx.schema.ReferenceElement that = (org.jsonx.schema.ReferenceElement)obj;
      if (!org.libj.lang.ObjectUtil.equals(nullable, that.nullable))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(minOccurs, that.minOccurs))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(maxOccurs, that.maxOccurs))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 1837032716 * 31 + super.hashCode();
      if (nullable != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(nullable);

      if (minOccurs != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(minOccurs);

      if (maxOccurs != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(maxOccurs);

      return hashCode;
    }
  }

  public static class ReferenceProperty extends org.jsonx.schema.Reference {
    @org.jsonx.BooleanProperty(name="nullable", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.Boolean getNullable() {
      return nullable;
    }

    public void setNullable(final java.lang.Boolean nullable) {
      this.nullable = nullable;
    }

    private java.lang.Boolean nullable;

    @org.jsonx.StringProperty(name="use", pattern="(required|optional)", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getUse() {
      return use;
    }

    public void setUse(final java.lang.String use) {
      this.use = use;
    }

    private java.lang.String use;

    @org.jsonx.ArrayProperty(name="bindings", type=org.jsonx.schema.FieldBindings.class, use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.util.List<org.jsonx.schema.FieldBinding> getBindings() {
      return bindings;
    }

    public void setBindings(final java.util.List<org.jsonx.schema.FieldBinding> bindings) {
      this.bindings = bindings;
    }

    private java.util.List<org.jsonx.schema.FieldBinding> bindings;

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.ReferenceProperty) || !super.equals(obj))
        return false;

      final org.jsonx.schema.ReferenceProperty that = (org.jsonx.schema.ReferenceProperty)obj;
      if (!org.libj.lang.ObjectUtil.equals(nullable, that.nullable))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(use, that.use))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(bindings, that.bindings))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 128637381 * 31 + super.hashCode();
      if (nullable != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(nullable);

      if (use != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(use);

      if (bindings != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(bindings);

      return hashCode;
    }
  }

  public static class Schema extends org.jsonx.schema.Documented {
    @org.jsonx.StringProperty(name="jx:ns", pattern="http://www.jsonx.org/schema-0.4.jsd", nullable=false)
    public java.lang.String getJx3aNs() {
      return jx3aNs;
    }

    public void setJx3aNs(final java.lang.String jx3aNs) {
      this.jx3aNs = jx3aNs;
    }

    private java.lang.String jx3aNs;

    @org.jsonx.StringProperty(name="jx:schemaLocation", pattern="http://www.jsonx.org/schema-0.4.jsd [^ ]+", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getJx3aSchemaLocation() {
      return jx3aSchemaLocation;
    }

    public void setJx3aSchemaLocation(final java.lang.String jx3aSchemaLocation) {
      this.jx3aSchemaLocation = jx3aSchemaLocation;
    }

    private java.lang.String jx3aSchemaLocation;

    @org.jsonx.AnyProperty(name="[a-zA-Z_][-a-zA-Z\\d_]*", types={@org.jsonx.t(objects=org.jsonx.schema.Array.class), @org.jsonx.t(objects=org.jsonx.schema.Boolean.class), @org.jsonx.t(objects=org.jsonx.schema.Number.class), @org.jsonx.t(objects=org.jsonx.schema.ObjectType.class), @org.jsonx.t(objects=org.jsonx.schema.String.class)}, nullable=false)
    public java.util.LinkedHashMap<java.lang.String,? extends org.jsonx.schema.Member> getDeclarations() {
      return declarations;
    }

    public void setDeclarations(final java.util.LinkedHashMap<java.lang.String,? extends org.jsonx.schema.Member> declarations) {
      this.declarations = declarations;
    }

    private java.util.LinkedHashMap<java.lang.String,? extends org.jsonx.schema.Member> declarations;

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.Schema) || !super.equals(obj))
        return false;

      final org.jsonx.schema.Schema that = (org.jsonx.schema.Schema)obj;
      if (!org.libj.lang.ObjectUtil.equals(jx3aNs, that.jx3aNs))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(jx3aSchemaLocation, that.jx3aSchemaLocation))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(declarations, that.declarations))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 1381524284 * 31 + super.hashCode();
      if (jx3aNs != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(jx3aNs);

      if (jx3aSchemaLocation != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(jx3aSchemaLocation);

      if (declarations != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(declarations);

      return hashCode;
    }
  }

  public static class String extends org.jsonx.schema.Member {
    @org.jsonx.StringProperty(name="jx:type", pattern="string", nullable=false)
    public java.lang.String getJx3aType() {
      return jx3aType;
    }

    public void setJx3aType(final java.lang.String jx3aType) {
      this.jx3aType = jx3aType;
    }

    private java.lang.String jx3aType;

    @org.jsonx.StringProperty(name="pattern", pattern="\\S+( \\S+)*", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getPattern() {
      return pattern;
    }

    public void setPattern(final java.lang.String pattern) {
      this.pattern = pattern;
    }

    private java.lang.String pattern;

    @org.jsonx.ArrayProperty(name="bindings", type=org.jsonx.schema.TypeBindings.class, use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.util.List<org.jsonx.schema.TypeBinding> getBindings() {
      return bindings;
    }

    public void setBindings(final java.util.List<org.jsonx.schema.TypeBinding> bindings) {
      this.bindings = bindings;
    }

    private java.util.List<org.jsonx.schema.TypeBinding> bindings;

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.String) || !super.equals(obj))
        return false;

      final org.jsonx.schema.String that = (org.jsonx.schema.String)obj;
      if (!org.libj.lang.ObjectUtil.equals(jx3aType, that.jx3aType))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(pattern, that.pattern))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(bindings, that.bindings))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 1397525932 * 31 + super.hashCode();
      if (jx3aType != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(jx3aType);

      if (pattern != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(pattern);

      if (bindings != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(bindings);

      return hashCode;
    }
  }

  public static class StringElement extends org.jsonx.schema.String {
    @org.jsonx.BooleanProperty(name="nullable", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.Boolean getNullable() {
      return nullable;
    }

    public void setNullable(final java.lang.Boolean nullable) {
      this.nullable = nullable;
    }

    private java.lang.Boolean nullable;

    @org.jsonx.StringProperty(name="minOccurs", pattern="[\\d]+", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getMinOccurs() {
      return minOccurs;
    }

    public void setMinOccurs(final java.lang.String minOccurs) {
      this.minOccurs = minOccurs;
    }

    private java.lang.String minOccurs;

    @org.jsonx.StringProperty(name="maxOccurs", pattern="([\\d]+|unbounded)", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getMaxOccurs() {
      return maxOccurs;
    }

    public void setMaxOccurs(final java.lang.String maxOccurs) {
      this.maxOccurs = maxOccurs;
    }

    private java.lang.String maxOccurs;

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.StringElement) || !super.equals(obj))
        return false;

      final org.jsonx.schema.StringElement that = (org.jsonx.schema.StringElement)obj;
      if (!org.libj.lang.ObjectUtil.equals(nullable, that.nullable))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(minOccurs, that.minOccurs))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(maxOccurs, that.maxOccurs))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 1884336048 * 31 + super.hashCode();
      if (nullable != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(nullable);

      if (minOccurs != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(minOccurs);

      if (maxOccurs != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(maxOccurs);

      return hashCode;
    }
  }

  public static class StringProperty extends org.jsonx.schema.String {
    @org.jsonx.BooleanProperty(name="nullable", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.Boolean getNullable() {
      return nullable;
    }

    public void setNullable(final java.lang.Boolean nullable) {
      this.nullable = nullable;
    }

    private java.lang.Boolean nullable;

    @org.jsonx.StringProperty(name="use", pattern="(required|optional)", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getUse() {
      return use;
    }

    public void setUse(final java.lang.String use) {
      this.use = use;
    }

    private java.lang.String use;

    @org.jsonx.ArrayProperty(name="bindings", type=org.jsonx.schema.TypeFieldBindings.class, use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.util.List<org.jsonx.schema.TypeBinding> getBindings() {
      return (java.util.List<org.jsonx.schema.TypeBinding>)super.getBindings();
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.StringProperty) || !super.equals(obj))
        return false;

      final org.jsonx.schema.StringProperty that = (org.jsonx.schema.StringProperty)obj;
      if (!org.libj.lang.ObjectUtil.equals(nullable, that.nullable))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(use, that.use))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 1595040673 * 31 + super.hashCode();
      if (nullable != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(nullable);

      if (use != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(use);

      return hashCode;
    }
  }

  /** Specifies language-specific binding. **/
  public static class TypeBinding extends org.jsonx.schema.Binding {
    /** Specifies the "type" qualified identifier. **/
    @org.jsonx.StringProperty(name="type", pattern="(([a-zA-Z_$][a-zA-Z\\d_$]*)\\.)*[a-zA-Z_$][a-zA-Z\\d_$]*(\\[\\])?", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getType() {
      return type;
    }

    /** Specifies the "type" qualified identifier. **/
    public void setType(final java.lang.String type) {
      this.type = type;
    }

    private java.lang.String type;

    /** Specifies the "decode" qualified function identifier that accepts input as a string, or as the native JSON type of this property, and returns an output of the specified "type" (or the default type if "type" is unspecified). **/
    @org.jsonx.StringProperty(name="decode", pattern="(([a-zA-Z_$][a-zA-Z\\d_$]*)\\.)*[a-zA-Z_$][a-zA-Z\\d_$]*(\\.<init>)?", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getDecode() {
      return decode;
    }

    /** Specifies the "decode" qualified function identifier that accepts input as a string, or as the native JSON type of this property, and returns an output of the specified "type" (or the default type if "type" is unspecified). **/
    public void setDecode(final java.lang.String decode) {
      this.decode = decode;
    }

    private java.lang.String decode;

    /** Specifies the "encode" qualified function identifier that accepts input of the type specified in "type" (or the default type if "type" is unspecified), and returns an output as a string, or as the native JSON type of this property. **/
    @org.jsonx.StringProperty(name="encode", pattern="(([a-zA-Z_$][a-zA-Z\\d_$]*)\\.)*[a-zA-Z_$][a-zA-Z\\d_$]*(\\.<init>)?", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getEncode() {
      return encode;
    }

    /** Specifies the "encode" qualified function identifier that accepts input of the type specified in "type" (or the default type if "type" is unspecified), and returns an output as a string, or as the native JSON type of this property. **/
    public void setEncode(final java.lang.String encode) {
      this.encode = encode;
    }

    private java.lang.String encode;

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.TypeBinding) || !super.equals(obj))
        return false;

      final org.jsonx.schema.TypeBinding that = (org.jsonx.schema.TypeBinding)obj;
      if (!org.libj.lang.ObjectUtil.equals(type, that.type))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(decode, that.decode))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(encode, that.encode))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 559947056 * 31 + super.hashCode();
      if (type != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(type);

      if (decode != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(decode);

      if (encode != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(encode);

      return hashCode;
    }
  }

  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  @org.jsonx.ObjectElement(id=0, type=org.jsonx.schema.TypeBinding.class, nullable=false)
  @org.jsonx.ArrayType(elementIds={0})
  /** Specifies language-specific bindings. **/
  public static @interface TypeBindings {
  }

  /** Specifies language-specific binding. **/
  public static class TypeFieldBinding extends org.jsonx.schema.TypeBinding {
    /** Specifies the "field" identifier. **/
    @org.jsonx.StringProperty(name="field", pattern="[a-zA-Z_$][a-zA-Z\\d_$]*", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getField() {
      return field;
    }

    /** Specifies the "field" identifier. **/
    public void setField(final java.lang.String field) {
      this.field = field;
    }

    private java.lang.String field;

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.TypeFieldBinding) || !super.equals(obj))
        return false;

      final org.jsonx.schema.TypeFieldBinding that = (org.jsonx.schema.TypeFieldBinding)obj;
      if (!org.libj.lang.ObjectUtil.equals(field, that.field))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = -1642693536 * 31 + super.hashCode();
      if (field != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(field);

      return hashCode;
    }
  }

  @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
  @org.jsonx.ObjectElement(id=0, type=org.jsonx.schema.TypeFieldBinding.class, nullable=false)
  @org.jsonx.ArrayType(elementIds={0})
  /** Specifies language-specific bindings. **/
  public static @interface TypeFieldBindings {
  }
}