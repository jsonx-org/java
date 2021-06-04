package org.jsonx;

@java.lang.SuppressWarnings("all")
public class schema {
  public abstract static class Any extends org.jsonx.schema.Member {
    @org.jsonx.StringProperty(name="jx:type", pattern="any", nullable=false)
    public java.lang.String getJx3aType() {
      return jx3aType;
    }

    public Any setJx3aType(final java.lang.String jx3aType) {
      this.jx3aType = jx3aType;
      return this;
    }

    private java.lang.String jx3aType;

    @org.jsonx.StringProperty(name="types", pattern="\\S+( \\S+)*", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getTypes() {
      return types;
    }

    public Any setTypes(final java.lang.String types) {
      this.types = types;
      return this;
    }

    private java.lang.String types;

    /** Defines text comments. Optional. **/
    public Any setDoc(final java.lang.String doc) {
      super.setDoc(doc);
      return (Any)this;
    }

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

    public AnyElement setNullable(final java.lang.Boolean nullable) {
      this.nullable = nullable;
      return this;
    }

    private java.lang.Boolean nullable;

    @org.jsonx.StringProperty(name="minOccurs", pattern="[\\d]+", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getMinOccurs() {
      return minOccurs;
    }

    public AnyElement setMinOccurs(final java.lang.String minOccurs) {
      this.minOccurs = minOccurs;
      return this;
    }

    private java.lang.String minOccurs;

    @org.jsonx.StringProperty(name="maxOccurs", pattern="([\\d]+|unbounded)", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getMaxOccurs() {
      return maxOccurs;
    }

    public AnyElement setMaxOccurs(final java.lang.String maxOccurs) {
      this.maxOccurs = maxOccurs;
      return this;
    }

    private java.lang.String maxOccurs;

    public AnyElement setJx3aType(final java.lang.String jx3aType) {
      super.setJx3aType(jx3aType);
      return (AnyElement)this;
    }

    public AnyElement setTypes(final java.lang.String types) {
      super.setTypes(types);
      return (AnyElement)this;
    }

    /** Defines text comments. Optional. **/
    public AnyElement setDoc(final java.lang.String doc) {
      super.setDoc(doc);
      return (AnyElement)this;
    }

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

    public AnyProperty setNullable(final java.lang.Boolean nullable) {
      this.nullable = nullable;
      return this;
    }

    private java.lang.Boolean nullable;

    @org.jsonx.StringProperty(name="use", pattern="(required|optional)", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getUse() {
      return use;
    }

    public AnyProperty setUse(final java.lang.String use) {
      this.use = use;
      return this;
    }

    private java.lang.String use;

    @org.jsonx.ArrayProperty(name="bindings", type=org.jsonx.schema.FieldBindings.class, use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.util.List<org.jsonx.schema.FieldBinding> getBindings() {
      return bindings;
    }

    public AnyProperty setBindings(final java.util.List<org.jsonx.schema.FieldBinding> bindings) {
      this.bindings = bindings;
      return this;
    }

    private java.util.List<org.jsonx.schema.FieldBinding> bindings;

    public AnyProperty setJx3aType(final java.lang.String jx3aType) {
      super.setJx3aType(jx3aType);
      return (AnyProperty)this;
    }

    public AnyProperty setTypes(final java.lang.String types) {
      super.setTypes(types);
      return (AnyProperty)this;
    }

    /** Defines text comments. Optional. **/
    public AnyProperty setDoc(final java.lang.String doc) {
      super.setDoc(doc);
      return (AnyProperty)this;
    }

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

    public Array setJx3aType(final java.lang.String jx3aType) {
      this.jx3aType = jx3aType;
      return this;
    }

    private java.lang.String jx3aType;

    /** Specifies the minimum inclusive number of iterations of child elements. Default: 1. **/
    @org.jsonx.StringProperty(name="minIterate", pattern="[\\d]+", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getMinIterate() {
      return minIterate;
    }

    /** Specifies the minimum inclusive number of iterations of child elements. Default: 1. **/
    public Array setMinIterate(final java.lang.String minIterate) {
      this.minIterate = minIterate;
      return this;
    }

    private java.lang.String minIterate;

    /** Specifies the maximum inclusive number of iterations of child elements. Default: 1. **/
    @org.jsonx.StringProperty(name="maxIterate", pattern="([\\d]+|unbounded)", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getMaxIterate() {
      return maxIterate;
    }

    /** Specifies the maximum inclusive number of iterations of child elements. Default: 1. **/
    public Array setMaxIterate(final java.lang.String maxIterate) {
      this.maxIterate = maxIterate;
      return this;
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

    public Array setElements(final java.util.List<org.jsonx.schema.Member> elements) {
      this.elements = elements;
      return this;
    }

    private java.util.List<org.jsonx.schema.Member> elements;

    /** Defines text comments. Optional. **/
    public Array setDoc(final java.lang.String doc) {
      super.setDoc(doc);
      return (Array)this;
    }

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

    public ArrayElement setNullable(final java.lang.Boolean nullable) {
      this.nullable = nullable;
      return this;
    }

    private java.lang.Boolean nullable;

    @org.jsonx.StringProperty(name="minOccurs", pattern="[\\d]+", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getMinOccurs() {
      return minOccurs;
    }

    public ArrayElement setMinOccurs(final java.lang.String minOccurs) {
      this.minOccurs = minOccurs;
      return this;
    }

    private java.lang.String minOccurs;

    @org.jsonx.StringProperty(name="maxOccurs", pattern="([\\d]+|unbounded)", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getMaxOccurs() {
      return maxOccurs;
    }

    public ArrayElement setMaxOccurs(final java.lang.String maxOccurs) {
      this.maxOccurs = maxOccurs;
      return this;
    }

    private java.lang.String maxOccurs;

    public ArrayElement setJx3aType(final java.lang.String jx3aType) {
      super.setJx3aType(jx3aType);
      return (ArrayElement)this;
    }

    /** Specifies the minimum inclusive number of iterations of child elements. Default: 1. **/
    public ArrayElement setMinIterate(final java.lang.String minIterate) {
      super.setMinIterate(minIterate);
      return (ArrayElement)this;
    }

    /** Specifies the maximum inclusive number of iterations of child elements. Default: 1. **/
    public ArrayElement setMaxIterate(final java.lang.String maxIterate) {
      super.setMaxIterate(maxIterate);
      return (ArrayElement)this;
    }

    /** Defines text comments. Optional. **/
    public ArrayElement setDoc(final java.lang.String doc) {
      super.setDoc(doc);
      return (ArrayElement)this;
    }

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

    public ArrayProperty setNullable(final java.lang.Boolean nullable) {
      this.nullable = nullable;
      return this;
    }

    private java.lang.Boolean nullable;

    @org.jsonx.StringProperty(name="use", pattern="(required|optional)", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getUse() {
      return use;
    }

    public ArrayProperty setUse(final java.lang.String use) {
      this.use = use;
      return this;
    }

    private java.lang.String use;

    @org.jsonx.ArrayProperty(name="bindings", type=org.jsonx.schema.FieldBindings.class, use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.util.List<org.jsonx.schema.FieldBinding> getBindings() {
      return bindings;
    }

    public ArrayProperty setBindings(final java.util.List<org.jsonx.schema.FieldBinding> bindings) {
      this.bindings = bindings;
      return this;
    }

    private java.util.List<org.jsonx.schema.FieldBinding> bindings;

    public ArrayProperty setJx3aType(final java.lang.String jx3aType) {
      super.setJx3aType(jx3aType);
      return (ArrayProperty)this;
    }

    /** Specifies the minimum inclusive number of iterations of child elements. Default: 1. **/
    public ArrayProperty setMinIterate(final java.lang.String minIterate) {
      super.setMinIterate(minIterate);
      return (ArrayProperty)this;
    }

    /** Specifies the maximum inclusive number of iterations of child elements. Default: 1. **/
    public ArrayProperty setMaxIterate(final java.lang.String maxIterate) {
      super.setMaxIterate(maxIterate);
      return (ArrayProperty)this;
    }

    /** Defines text comments. Optional. **/
    public ArrayProperty setDoc(final java.lang.String doc) {
      super.setDoc(doc);
      return (ArrayProperty)this;
    }

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
    public Binding setLang(final java.lang.String lang) {
      this.lang = lang;
      return this;
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
      return org.jsonx.JxEncoder.get().toString(this);
    }
  }

  public static class Boolean extends org.jsonx.schema.Member {
    @org.jsonx.StringProperty(name="jx:type", pattern="boolean", nullable=false)
    public java.lang.String getJx3aType() {
      return jx3aType;
    }

    public Boolean setJx3aType(final java.lang.String jx3aType) {
      this.jx3aType = jx3aType;
      return this;
    }

    private java.lang.String jx3aType;

    @org.jsonx.ArrayProperty(name="bindings", type=org.jsonx.schema.TypeBindings.class, use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.util.List<org.jsonx.schema.TypeBinding> getBindings() {
      return bindings;
    }

    public Boolean setBindings(final java.util.List<org.jsonx.schema.TypeBinding> bindings) {
      this.bindings = bindings;
      return this;
    }

    private java.util.List<org.jsonx.schema.TypeBinding> bindings;

    /** Defines text comments. Optional. **/
    public Boolean setDoc(final java.lang.String doc) {
      super.setDoc(doc);
      return (Boolean)this;
    }

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

    public BooleanElement setNullable(final java.lang.Boolean nullable) {
      this.nullable = nullable;
      return this;
    }

    private java.lang.Boolean nullable;

    @org.jsonx.StringProperty(name="minOccurs", pattern="[\\d]+", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getMinOccurs() {
      return minOccurs;
    }

    public BooleanElement setMinOccurs(final java.lang.String minOccurs) {
      this.minOccurs = minOccurs;
      return this;
    }

    private java.lang.String minOccurs;

    @org.jsonx.StringProperty(name="maxOccurs", pattern="([\\d]+|unbounded)", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getMaxOccurs() {
      return maxOccurs;
    }

    public BooleanElement setMaxOccurs(final java.lang.String maxOccurs) {
      this.maxOccurs = maxOccurs;
      return this;
    }

    private java.lang.String maxOccurs;

    public BooleanElement setJx3aType(final java.lang.String jx3aType) {
      super.setJx3aType(jx3aType);
      return (BooleanElement)this;
    }

    /** Defines text comments. Optional. **/
    public BooleanElement setDoc(final java.lang.String doc) {
      super.setDoc(doc);
      return (BooleanElement)this;
    }

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

    public BooleanProperty setNullable(final java.lang.Boolean nullable) {
      this.nullable = nullable;
      return this;
    }

    private java.lang.Boolean nullable;

    @org.jsonx.StringProperty(name="use", pattern="(required|optional)", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getUse() {
      return use;
    }

    public BooleanProperty setUse(final java.lang.String use) {
      this.use = use;
      return this;
    }

    private java.lang.String use;

    @org.jsonx.ArrayProperty(name="bindings", type=org.jsonx.schema.TypeFieldBindings.class, use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.util.List<org.jsonx.schema.TypeBinding> getBindings() {
      return super.getBindings();
    }

    public BooleanProperty setJx3aType(final java.lang.String jx3aType) {
      super.setJx3aType(jx3aType);
      return (BooleanProperty)this;
    }

    /** Defines text comments. Optional. **/
    public BooleanProperty setDoc(final java.lang.String doc) {
      super.setDoc(doc);
      return (BooleanProperty)this;
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
    public Documented setDoc(final java.lang.String doc) {
      this.doc = doc;
      return this;
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
      return org.jsonx.JxEncoder.get().toString(this);
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
    public FieldBinding setField(final java.lang.String field) {
      this.field = field;
      return this;
    }

    private java.lang.String field;

    /** Specifies the language to which this binding applies. **/
    public FieldBinding setLang(final java.lang.String lang) {
      super.setLang(lang);
      return (FieldBinding)this;
    }

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
    /** Defines text comments. Optional. **/
    public Member setDoc(final java.lang.String doc) {
      super.setDoc(doc);
      return (Member)this;
    }

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

    public Number setJx3aType(final java.lang.String jx3aType) {
      this.jx3aType = jx3aType;
      return this;
    }

    private java.lang.String jx3aType;

    /** Specifies the value range in interval notation:
           Open (exclusive) interval: (min,max)
           Closed (inclusive) interval: [min,max]
           Half-open or half-closed interval: [min,max)
           Degenerate interval: [val] **/
    @org.jsonx.StringProperty(name="range", pattern="[\\(\\[](-?(0|[1-9]\\d*)(\\.\\d+)?([eE][+-]?([1-9]\\d*))?)?,(-?(0|[1-9]\\d*)(\\.\\d+)?([eE][+-]?([1-9]\\d*))?)?[\\)\\]]", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getRange() {
      return range;
    }

    /** Specifies the value range in interval notation:
           Open (exclusive) interval: (min,max)
           Closed (inclusive) interval: [min,max]
           Half-open or half-closed interval: [min,max)
           Degenerate interval: [val] **/
    public Number setRange(final java.lang.String range) {
      this.range = range;
      return this;
    }

    private java.lang.String range;

    /** The number of digits to the right of the decimal point. If a value is not specified, the scale is unbounded. **/
    @org.jsonx.NumberProperty(name="scale", scale=0, use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.math.BigInteger getScale() {
      return scale;
    }

    /** The number of digits to the right of the decimal point. If a value is not specified, the scale is unbounded. **/
    public Number setScale(final java.math.BigInteger scale) {
      this.scale = scale;
      return this;
    }

    private java.math.BigInteger scale;

    @org.jsonx.ArrayProperty(name="bindings", type=org.jsonx.schema.TypeBindings.class, use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.util.List<org.jsonx.schema.TypeBinding> getBindings() {
      return bindings;
    }

    public Number setBindings(final java.util.List<org.jsonx.schema.TypeBinding> bindings) {
      this.bindings = bindings;
      return this;
    }

    private java.util.List<org.jsonx.schema.TypeBinding> bindings;

    /** Defines text comments. Optional. **/
    public Number setDoc(final java.lang.String doc) {
      super.setDoc(doc);
      return (Number)this;
    }

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

    public NumberElement setNullable(final java.lang.Boolean nullable) {
      this.nullable = nullable;
      return this;
    }

    private java.lang.Boolean nullable;

    @org.jsonx.StringProperty(name="minOccurs", pattern="[\\d]+", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getMinOccurs() {
      return minOccurs;
    }

    public NumberElement setMinOccurs(final java.lang.String minOccurs) {
      this.minOccurs = minOccurs;
      return this;
    }

    private java.lang.String minOccurs;

    @org.jsonx.StringProperty(name="maxOccurs", pattern="([\\d]+|unbounded)", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getMaxOccurs() {
      return maxOccurs;
    }

    public NumberElement setMaxOccurs(final java.lang.String maxOccurs) {
      this.maxOccurs = maxOccurs;
      return this;
    }

    private java.lang.String maxOccurs;

    public NumberElement setJx3aType(final java.lang.String jx3aType) {
      super.setJx3aType(jx3aType);
      return (NumberElement)this;
    }

    /** Specifies the value range in interval notation:
           Open (exclusive) interval: (min,max)
           Closed (inclusive) interval: [min,max]
           Half-open or half-closed interval: [min,max)
           Degenerate interval: [val] **/
    public NumberElement setRange(final java.lang.String range) {
      super.setRange(range);
      return (NumberElement)this;
    }

    /** The number of digits to the right of the decimal point. If a value is not specified, the scale is unbounded. **/
    public NumberElement setScale(final java.math.BigInteger scale) {
      super.setScale(scale);
      return (NumberElement)this;
    }

    /** Defines text comments. Optional. **/
    public NumberElement setDoc(final java.lang.String doc) {
      super.setDoc(doc);
      return (NumberElement)this;
    }

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

    public NumberProperty setNullable(final java.lang.Boolean nullable) {
      this.nullable = nullable;
      return this;
    }

    private java.lang.Boolean nullable;

    @org.jsonx.StringProperty(name="use", pattern="(required|optional)", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getUse() {
      return use;
    }

    public NumberProperty setUse(final java.lang.String use) {
      this.use = use;
      return this;
    }

    private java.lang.String use;

    @org.jsonx.ArrayProperty(name="bindings", type=org.jsonx.schema.TypeFieldBindings.class, use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.util.List<org.jsonx.schema.TypeBinding> getBindings() {
      return super.getBindings();
    }

    public NumberProperty setJx3aType(final java.lang.String jx3aType) {
      super.setJx3aType(jx3aType);
      return (NumberProperty)this;
    }

    /** Specifies the value range in interval notation:
           Open (exclusive) interval: (min,max)
           Closed (inclusive) interval: [min,max]
           Half-open or half-closed interval: [min,max)
           Degenerate interval: [val] **/
    public NumberProperty setRange(final java.lang.String range) {
      super.setRange(range);
      return (NumberProperty)this;
    }

    /** The number of digits to the right of the decimal point. If a value is not specified, the scale is unbounded. **/
    public NumberProperty setScale(final java.math.BigInteger scale) {
      super.setScale(scale);
      return (NumberProperty)this;
    }

    /** Defines text comments. Optional. **/
    public NumberProperty setDoc(final java.lang.String doc) {
      super.setDoc(doc);
      return (NumberProperty)this;
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

      public Properties setProperties(final java.util.LinkedHashMap<java.lang.String,? extends org.jsonx.schema.Member> properties) {
        this.properties = properties;
        return this;
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
        return org.jsonx.JxEncoder.get().toString(this);
      }
    }

    @org.jsonx.StringProperty(name="jx:type", pattern="object", nullable=false)
    public java.lang.String getJx3aType() {
      return jx3aType;
    }

    public Object setJx3aType(final java.lang.String jx3aType) {
      this.jx3aType = jx3aType;
      return this;
    }

    private java.lang.String jx3aType;

    /** Specifies the name of the type to extend. Optional. **/
    @org.jsonx.StringProperty(name="extends", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getExtends() {
      return _extends;
    }

    /** Specifies the name of the type to extend. Optional. **/
    public Object setExtends(final java.lang.String _extends) {
      this._extends = _extends;
      return this;
    }

    private java.lang.String _extends;

    @org.jsonx.ObjectProperty(name="properties", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public org.jsonx.schema.Object.Properties getProperties() {
      return properties;
    }

    public Object setProperties(final org.jsonx.schema.Object.Properties properties) {
      this.properties = properties;
      return this;
    }

    private org.jsonx.schema.Object.Properties properties;

    /** Defines text comments. Optional. **/
    public Object setDoc(final java.lang.String doc) {
      super.setDoc(doc);
      return (Object)this;
    }

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

    public ObjectElement setNullable(final java.lang.Boolean nullable) {
      this.nullable = nullable;
      return this;
    }

    private java.lang.Boolean nullable;

    @org.jsonx.StringProperty(name="minOccurs", pattern="[\\d]+", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getMinOccurs() {
      return minOccurs;
    }

    public ObjectElement setMinOccurs(final java.lang.String minOccurs) {
      this.minOccurs = minOccurs;
      return this;
    }

    private java.lang.String minOccurs;

    @org.jsonx.StringProperty(name="maxOccurs", pattern="([\\d]+|unbounded)", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getMaxOccurs() {
      return maxOccurs;
    }

    public ObjectElement setMaxOccurs(final java.lang.String maxOccurs) {
      this.maxOccurs = maxOccurs;
      return this;
    }

    private java.lang.String maxOccurs;

    public ObjectElement setJx3aType(final java.lang.String jx3aType) {
      super.setJx3aType(jx3aType);
      return (ObjectElement)this;
    }

    /** Specifies the name of the type to extend. Optional. **/
    public ObjectElement setExtends(final java.lang.String _extends) {
      super.setExtends(_extends);
      return (ObjectElement)this;
    }

    public ObjectElement setProperties(final org.jsonx.schema.Object.Properties properties) {
      super.setProperties(properties);
      return (ObjectElement)this;
    }

    /** Defines text comments. Optional. **/
    public ObjectElement setDoc(final java.lang.String doc) {
      super.setDoc(doc);
      return (ObjectElement)this;
    }

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

    public ObjectProperty setNullable(final java.lang.Boolean nullable) {
      this.nullable = nullable;
      return this;
    }

    private java.lang.Boolean nullable;

    @org.jsonx.StringProperty(name="use", pattern="(required|optional)", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getUse() {
      return use;
    }

    public ObjectProperty setUse(final java.lang.String use) {
      this.use = use;
      return this;
    }

    private java.lang.String use;

    @org.jsonx.ArrayProperty(name="bindings", type=org.jsonx.schema.FieldBindings.class, use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.util.List<org.jsonx.schema.FieldBinding> getBindings() {
      return bindings;
    }

    public ObjectProperty setBindings(final java.util.List<org.jsonx.schema.FieldBinding> bindings) {
      this.bindings = bindings;
      return this;
    }

    private java.util.List<org.jsonx.schema.FieldBinding> bindings;

    public ObjectProperty setJx3aType(final java.lang.String jx3aType) {
      super.setJx3aType(jx3aType);
      return (ObjectProperty)this;
    }

    /** Specifies the name of the type to extend. Optional. **/
    public ObjectProperty setExtends(final java.lang.String _extends) {
      super.setExtends(_extends);
      return (ObjectProperty)this;
    }

    public ObjectProperty setProperties(final org.jsonx.schema.Object.Properties properties) {
      super.setProperties(properties);
      return (ObjectProperty)this;
    }

    /** Defines text comments. Optional. **/
    public ObjectProperty setDoc(final java.lang.String doc) {
      super.setDoc(doc);
      return (ObjectProperty)this;
    }

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
    public ObjectType setAbstract(final java.lang.Boolean _abstract) {
      this._abstract = _abstract;
      return this;
    }

    private java.lang.Boolean _abstract;

    public ObjectType setJx3aType(final java.lang.String jx3aType) {
      super.setJx3aType(jx3aType);
      return (ObjectType)this;
    }

    /** Specifies the name of the type to extend. Optional. **/
    public ObjectType setExtends(final java.lang.String _extends) {
      super.setExtends(_extends);
      return (ObjectType)this;
    }

    public ObjectType setProperties(final org.jsonx.schema.Object.Properties properties) {
      super.setProperties(properties);
      return (ObjectType)this;
    }

    /** Defines text comments. Optional. **/
    public ObjectType setDoc(final java.lang.String doc) {
      super.setDoc(doc);
      return (ObjectType)this;
    }

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

    public Reference setJx3aType(final java.lang.String jx3aType) {
      this.jx3aType = jx3aType;
      return this;
    }

    private java.lang.String jx3aType;

    /** Specifies the name of the referenced type. Required. **/
    @org.jsonx.StringProperty(name="type", pattern="\\S+( \\S+)*", nullable=false)
    public java.lang.String getType() {
      return type;
    }

    /** Specifies the name of the referenced type. Required. **/
    public Reference setType(final java.lang.String type) {
      this.type = type;
      return this;
    }

    private java.lang.String type;

    /** Defines text comments. Optional. **/
    public Reference setDoc(final java.lang.String doc) {
      super.setDoc(doc);
      return (Reference)this;
    }

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

    public ReferenceElement setNullable(final java.lang.Boolean nullable) {
      this.nullable = nullable;
      return this;
    }

    private java.lang.Boolean nullable;

    @org.jsonx.StringProperty(name="minOccurs", pattern="[\\d]+", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getMinOccurs() {
      return minOccurs;
    }

    public ReferenceElement setMinOccurs(final java.lang.String minOccurs) {
      this.minOccurs = minOccurs;
      return this;
    }

    private java.lang.String minOccurs;

    @org.jsonx.StringProperty(name="maxOccurs", pattern="([\\d]+|unbounded)", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getMaxOccurs() {
      return maxOccurs;
    }

    public ReferenceElement setMaxOccurs(final java.lang.String maxOccurs) {
      this.maxOccurs = maxOccurs;
      return this;
    }

    private java.lang.String maxOccurs;

    public ReferenceElement setJx3aType(final java.lang.String jx3aType) {
      super.setJx3aType(jx3aType);
      return (ReferenceElement)this;
    }

    /** Specifies the name of the referenced type. Required. **/
    public ReferenceElement setType(final java.lang.String type) {
      super.setType(type);
      return (ReferenceElement)this;
    }

    /** Defines text comments. Optional. **/
    public ReferenceElement setDoc(final java.lang.String doc) {
      super.setDoc(doc);
      return (ReferenceElement)this;
    }

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

    public ReferenceProperty setNullable(final java.lang.Boolean nullable) {
      this.nullable = nullable;
      return this;
    }

    private java.lang.Boolean nullable;

    @org.jsonx.StringProperty(name="use", pattern="(required|optional)", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getUse() {
      return use;
    }

    public ReferenceProperty setUse(final java.lang.String use) {
      this.use = use;
      return this;
    }

    private java.lang.String use;

    @org.jsonx.ArrayProperty(name="bindings", type=org.jsonx.schema.FieldBindings.class, use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.util.List<org.jsonx.schema.FieldBinding> getBindings() {
      return bindings;
    }

    public ReferenceProperty setBindings(final java.util.List<org.jsonx.schema.FieldBinding> bindings) {
      this.bindings = bindings;
      return this;
    }

    private java.util.List<org.jsonx.schema.FieldBinding> bindings;

    public ReferenceProperty setJx3aType(final java.lang.String jx3aType) {
      super.setJx3aType(jx3aType);
      return (ReferenceProperty)this;
    }

    /** Specifies the name of the referenced type. Required. **/
    public ReferenceProperty setType(final java.lang.String type) {
      super.setType(type);
      return (ReferenceProperty)this;
    }

    /** Defines text comments. Optional. **/
    public ReferenceProperty setDoc(final java.lang.String doc) {
      super.setDoc(doc);
      return (ReferenceProperty)this;
    }

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

    public Schema setJx3aNs(final java.lang.String jx3aNs) {
      this.jx3aNs = jx3aNs;
      return this;
    }

    private java.lang.String jx3aNs;

    @org.jsonx.StringProperty(name="jx:schemaLocation", pattern="http://www.jsonx.org/schema-0.4.jsd [^ ]+", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getJx3aSchemaLocation() {
      return jx3aSchemaLocation;
    }

    public Schema setJx3aSchemaLocation(final java.lang.String jx3aSchemaLocation) {
      this.jx3aSchemaLocation = jx3aSchemaLocation;
      return this;
    }

    private java.lang.String jx3aSchemaLocation;

    @org.jsonx.AnyProperty(name="[a-zA-Z_][-a-zA-Z\\d_]*", types={@org.jsonx.t(objects=org.jsonx.schema.Array.class), @org.jsonx.t(objects=org.jsonx.schema.Boolean.class), @org.jsonx.t(objects=org.jsonx.schema.Number.class), @org.jsonx.t(objects=org.jsonx.schema.ObjectType.class), @org.jsonx.t(objects=org.jsonx.schema.String.class)}, nullable=false)
    public java.util.LinkedHashMap<java.lang.String,? extends org.jsonx.schema.Member> getDeclarations() {
      return declarations;
    }

    public Schema setDeclarations(final java.util.LinkedHashMap<java.lang.String,? extends org.jsonx.schema.Member> declarations) {
      this.declarations = declarations;
      return this;
    }

    private java.util.LinkedHashMap<java.lang.String,? extends org.jsonx.schema.Member> declarations;

    /** Defines text comments. Optional. **/
    public Schema setDoc(final java.lang.String doc) {
      super.setDoc(doc);
      return (Schema)this;
    }

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

    public String setJx3aType(final java.lang.String jx3aType) {
      this.jx3aType = jx3aType;
      return this;
    }

    private java.lang.String jx3aType;

    @org.jsonx.StringProperty(name="pattern", pattern="\\S+( \\S+)*", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getPattern() {
      return pattern;
    }

    public String setPattern(final java.lang.String pattern) {
      this.pattern = pattern;
      return this;
    }

    private java.lang.String pattern;

    @org.jsonx.ArrayProperty(name="bindings", type=org.jsonx.schema.TypeBindings.class, use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.util.List<org.jsonx.schema.TypeBinding> getBindings() {
      return bindings;
    }

    public String setBindings(final java.util.List<org.jsonx.schema.TypeBinding> bindings) {
      this.bindings = bindings;
      return this;
    }

    private java.util.List<org.jsonx.schema.TypeBinding> bindings;

    /** Defines text comments. Optional. **/
    public String setDoc(final java.lang.String doc) {
      super.setDoc(doc);
      return (String)this;
    }

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

    public StringElement setNullable(final java.lang.Boolean nullable) {
      this.nullable = nullable;
      return this;
    }

    private java.lang.Boolean nullable;

    @org.jsonx.StringProperty(name="minOccurs", pattern="[\\d]+", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getMinOccurs() {
      return minOccurs;
    }

    public StringElement setMinOccurs(final java.lang.String minOccurs) {
      this.minOccurs = minOccurs;
      return this;
    }

    private java.lang.String minOccurs;

    @org.jsonx.StringProperty(name="maxOccurs", pattern="([\\d]+|unbounded)", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getMaxOccurs() {
      return maxOccurs;
    }

    public StringElement setMaxOccurs(final java.lang.String maxOccurs) {
      this.maxOccurs = maxOccurs;
      return this;
    }

    private java.lang.String maxOccurs;

    public StringElement setJx3aType(final java.lang.String jx3aType) {
      super.setJx3aType(jx3aType);
      return (StringElement)this;
    }

    public StringElement setPattern(final java.lang.String pattern) {
      super.setPattern(pattern);
      return (StringElement)this;
    }

    /** Defines text comments. Optional. **/
    public StringElement setDoc(final java.lang.String doc) {
      super.setDoc(doc);
      return (StringElement)this;
    }

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

    public StringProperty setNullable(final java.lang.Boolean nullable) {
      this.nullable = nullable;
      return this;
    }

    private java.lang.Boolean nullable;

    @org.jsonx.StringProperty(name="use", pattern="(required|optional)", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getUse() {
      return use;
    }

    public StringProperty setUse(final java.lang.String use) {
      this.use = use;
      return this;
    }

    private java.lang.String use;

    @org.jsonx.ArrayProperty(name="bindings", type=org.jsonx.schema.TypeFieldBindings.class, use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.util.List<org.jsonx.schema.TypeBinding> getBindings() {
      return super.getBindings();
    }

    public StringProperty setJx3aType(final java.lang.String jx3aType) {
      super.setJx3aType(jx3aType);
      return (StringProperty)this;
    }

    public StringProperty setPattern(final java.lang.String pattern) {
      super.setPattern(pattern);
      return (StringProperty)this;
    }

    /** Defines text comments. Optional. **/
    public StringProperty setDoc(final java.lang.String doc) {
      super.setDoc(doc);
      return (StringProperty)this;
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
    public TypeBinding setType(final java.lang.String type) {
      this.type = type;
      return this;
    }

    private java.lang.String type;

    /** Specifies the "decode" qualified function identifier that accepts input as a string, or as the native JSON type of this property, and returns an output of the specified "type" (or the default type if "type" is unspecified). **/
    @org.jsonx.StringProperty(name="decode", pattern="(([a-zA-Z_$][a-zA-Z\\d_$]*)\\.)*[a-zA-Z_$][a-zA-Z\\d_$]*(\\.<init>)?", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getDecode() {
      return decode;
    }

    /** Specifies the "decode" qualified function identifier that accepts input as a string, or as the native JSON type of this property, and returns an output of the specified "type" (or the default type if "type" is unspecified). **/
    public TypeBinding setDecode(final java.lang.String decode) {
      this.decode = decode;
      return this;
    }

    private java.lang.String decode;

    /** Specifies the "encode" qualified function identifier that accepts input of the type specified in "type" (or the default type if "type" is unspecified), and returns an output as a string, or as the native JSON type of this property. **/
    @org.jsonx.StringProperty(name="encode", pattern="(([a-zA-Z_$][a-zA-Z\\d_$]*)\\.)*[a-zA-Z_$][a-zA-Z\\d_$]*(\\.<init>)?", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getEncode() {
      return encode;
    }

    /** Specifies the "encode" qualified function identifier that accepts input of the type specified in "type" (or the default type if "type" is unspecified), and returns an output as a string, or as the native JSON type of this property. **/
    public TypeBinding setEncode(final java.lang.String encode) {
      this.encode = encode;
      return this;
    }

    private java.lang.String encode;

    /** Specifies the language to which this binding applies. **/
    public TypeBinding setLang(final java.lang.String lang) {
      super.setLang(lang);
      return (TypeBinding)this;
    }

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
    public TypeFieldBinding setField(final java.lang.String field) {
      this.field = field;
      return this;
    }

    private java.lang.String field;

    /** Specifies the "type" qualified identifier. **/
    public TypeFieldBinding setType(final java.lang.String type) {
      super.setType(type);
      return (TypeFieldBinding)this;
    }

    /** Specifies the "decode" qualified function identifier that accepts input as a string, or as the native JSON type of this property, and returns an output of the specified "type" (or the default type if "type" is unspecified). **/
    public TypeFieldBinding setDecode(final java.lang.String decode) {
      super.setDecode(decode);
      return (TypeFieldBinding)this;
    }

    /** Specifies the "encode" qualified function identifier that accepts input of the type specified in "type" (or the default type if "type" is unspecified), and returns an output as a string, or as the native JSON type of this property. **/
    public TypeFieldBinding setEncode(final java.lang.String encode) {
      super.setEncode(encode);
      return (TypeFieldBinding)this;
    }

    /** Specifies the language to which this binding applies. **/
    public TypeFieldBinding setLang(final java.lang.String lang) {
      super.setLang(lang);
      return (TypeFieldBinding)this;
    }

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