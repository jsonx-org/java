package org.jsonx;

import org.jsonx.schema.Schema;

@java.lang.SuppressWarnings("all")
@javax.annotation.Generated(value="org.jsonx.Generator", date="2023-08-20T15:28:00.849")
public class binding {
  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/binding-0.4.jsdx")
  public static class Any extends org.jsonx.binding.FieldBindings {
    @org.jsonx.StringProperty(name="jx:type", pattern="any", nullable=false)
    public java.lang.String getJx3aType() {
      return jx3aType;
    }

    public Any setJx3aType(final java.lang.String jx3aType) {
      this.jx3aType = jx3aType;
      return this;
    }

    private java.lang.String jx3aType;

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.binding.Any) || !super.equals(obj))
        return false;

      final org.jsonx.binding.Any that = (org.jsonx.binding.Any)obj;
      if (!org.libj.lang.ObjectUtil.equals(jx3aType, that.jx3aType))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = -1967902139 * 31 + super.hashCode();
      if (jx3aType != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(jx3aType);

      return hashCode;
    }
  }

  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/binding-0.4.jsdx")
  public static class Array extends org.jsonx.binding.FieldBindings {
    @org.jsonx.StringProperty(name="jx:type", pattern="array", nullable=false)
    public java.lang.String getJx3aType() {
      return jx3aType;
    }

    public Array setJx3aType(final java.lang.String jx3aType) {
      this.jx3aType = jx3aType;
      return this;
    }

    private java.lang.String jx3aType;

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.binding.Array) || !super.equals(obj))
        return false;

      final org.jsonx.binding.Array that = (org.jsonx.binding.Array)obj;
      if (!org.libj.lang.ObjectUtil.equals(jx3aType, that.jx3aType))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = -1368229774 * 31 + super.hashCode();
      if (jx3aType != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(jx3aType);

      return hashCode;
    }
  }

  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/binding-0.4.jsdx")
  public static class Binding implements org.jsonx.JxObject {
    @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/binding-0.4.jsdx")
    public static class Bindings implements org.jsonx.JxObject {
      @org.jsonx.AnyProperty(name="\\S|\\S.*\\S", types={@org.jsonx.t(objects=org.jsonx.binding.Any.class), @org.jsonx.t(objects=org.jsonx.binding.Reference.class), @org.jsonx.t(objects=org.jsonx.binding.Array.class), @org.jsonx.t(objects=org.jsonx.binding.Object.class), @org.jsonx.t(objects=org.jsonx.binding.Boolean.class), @org.jsonx.t(objects=org.jsonx.binding.Number.class), @org.jsonx.t(objects=org.jsonx.binding.String.class)}, nullable=false)
      public java.util.LinkedHashMap<java.lang.String,java.lang.Object> get5cS7c5cS2e2a5cS() {
        return _5cS7c5cS2e2a5cS;
      }

      public Bindings set5cS7c5cS2e2a5cS(final java.util.LinkedHashMap<java.lang.String,java.lang.Object> _5cS7c5cS2e2a5cS) {
        this._5cS7c5cS2e2a5cS = _5cS7c5cS2e2a5cS;
        return this;
      }

      private java.util.LinkedHashMap<java.lang.String,java.lang.Object> _5cS7c5cS2e2a5cS;

      @java.lang.Override
      public boolean equals(final java.lang.Object obj) {
        if (obj == this)
          return true;

        if (!(obj instanceof org.jsonx.binding.Binding.Bindings))
          return false;

        final org.jsonx.binding.Binding.Bindings that = (org.jsonx.binding.Binding.Bindings)obj;
        if (!org.libj.lang.ObjectUtil.equals(_5cS7c5cS2e2a5cS, that._5cS7c5cS2e2a5cS))
          return false;

        return true;
      }

      @java.lang.Override
      public int hashCode() {
        int hashCode = -1005233612;
        if (_5cS7c5cS2e2a5cS != null)
          hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_5cS7c5cS2e2a5cS);

        return hashCode;
      }

      @java.lang.Override
      public java.lang.String toString() {
        return org.jsonx.JxEncoder.get().toString(this);
      }
    }

    @org.jsonx.StringProperty(name="jx:ns", pattern="http://www.jsonx.org/binding-0.4.jsd", nullable=false)
    public java.lang.String getJx3aNs() {
      return jx3aNs;
    }

    public Binding setJx3aNs(final java.lang.String jx3aNs) {
      this.jx3aNs = jx3aNs;
      return this;
    }

    private java.lang.String jx3aNs;

    @org.jsonx.StringProperty(name="jx:schemaLocation", pattern="((\\S|\\S.*\\S) (\\S|\\S.*\\S))+", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String getJx3aSchemaLocation() {
      return jx3aSchemaLocation;
    }

    public Binding setJx3aSchemaLocation(final java.lang.String jx3aSchemaLocation) {
      this.jx3aSchemaLocation = jx3aSchemaLocation;
      return this;
    }

    private java.lang.String jx3aSchemaLocation;

    @org.jsonx.ObjectProperty(name="schema", nullable=false)
    public org.jsonx.schema.Schema getSchema() {
      return schema;
    }

    public Binding setSchema(final org.jsonx.schema.Schema schema) {
      this.schema = schema;
      return this;
    }

    private org.jsonx.schema.Schema schema;

    @org.jsonx.ObjectProperty(name="bindings", nullable=false)
    public org.jsonx.binding.Binding.Bindings getBindings() {
      return bindings;
    }

    public Binding setBindings(final org.jsonx.binding.Binding.Bindings bindings) {
      this.bindings = bindings;
      return this;
    }

    private org.jsonx.binding.Binding.Bindings bindings;

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.binding.Binding))
        return false;

      final org.jsonx.binding.Binding that = (org.jsonx.binding.Binding)obj;
      if (!org.libj.lang.ObjectUtil.equals(jx3aNs, that.jx3aNs))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(jx3aSchemaLocation, that.jx3aSchemaLocation))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(schema, that.schema))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(bindings, that.bindings))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 17404510;
      if (jx3aNs != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(jx3aNs);

      if (jx3aSchemaLocation != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(jx3aSchemaLocation);

      if (schema != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(schema);

      if (bindings != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(bindings);

      return hashCode;
    }

    @java.lang.Override
    public java.lang.String toString() {
      return org.jsonx.JxEncoder.get().toString(this);
    }
  }

  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/binding-0.4.jsdx")
  public static class Boolean extends org.jsonx.binding.TypeFieldBindings {
    @org.jsonx.StringProperty(name="jx:type", pattern="boolean", nullable=false)
    public java.lang.String getJx3aType() {
      return jx3aType;
    }

    public Boolean setJx3aType(final java.lang.String jx3aType) {
      this.jx3aType = jx3aType;
      return this;
    }

    private java.lang.String jx3aType;

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.binding.Boolean) || !super.equals(obj))
        return false;

      final org.jsonx.binding.Boolean that = (org.jsonx.binding.Boolean)obj;
      if (!org.libj.lang.ObjectUtil.equals(jx3aType, that.jx3aType))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 190337025 * 31 + super.hashCode();
      if (jx3aType != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(jx3aType);

      return hashCode;
    }
  }

  /** Specifies language-specific binding. **/
  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/binding-0.4.jsdx")
  public static class FieldBinding extends org.jsonx.binding.Lang {
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

      if (!(obj instanceof org.jsonx.binding.FieldBinding) || !super.equals(obj))
        return false;

      final org.jsonx.binding.FieldBinding that = (org.jsonx.binding.FieldBinding)obj;
      if (!org.libj.lang.ObjectUtil.equals(field, that.field))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 361567218 * 31 + super.hashCode();
      if (field != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(field);

      return hashCode;
    }
  }

  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/binding-0.4.jsdx")
  public abstract static class FieldBindings extends org.jsonx.binding.Path {
    @org.jsonx.ObjectElement(id=0, type=org.jsonx.binding.FieldBinding.class, nullable=false)
    @org.jsonx.ArrayProperty(name="bindings", elementIds={0}, nullable=false)
    public java.util.List<org.jsonx.binding.FieldBinding> getBindings() {
      return bindings;
    }

    public FieldBindings setBindings(final java.util.List<org.jsonx.binding.FieldBinding> bindings) {
      this.bindings = bindings;
      return this;
    }

    private java.util.List<org.jsonx.binding.FieldBinding> bindings;

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.binding.FieldBindings) || !super.equals(obj))
        return false;

      final org.jsonx.binding.FieldBindings that = (org.jsonx.binding.FieldBindings)obj;
      if (!org.libj.lang.ObjectUtil.equals(bindings, that.bindings))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = -1676318015 * 31 + super.hashCode();
      if (bindings != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(bindings);

      return hashCode;
    }
  }

  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/binding-0.4.jsdx")
  public abstract static class Lang implements org.jsonx.JxObject {
    /** Specifies the language to which this binding applies. **/
    @org.jsonx.StringProperty(name="lang", pattern="\\S|\\S.*\\S", nullable=false)
    public java.lang.String getLang() {
      return lang;
    }

    /** Specifies the language to which this binding applies. **/
    public Lang setLang(final java.lang.String lang) {
      this.lang = lang;
      return this;
    }

    private java.lang.String lang;

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.binding.Lang))
        return false;

      final org.jsonx.binding.Lang that = (org.jsonx.binding.Lang)obj;
      if (!org.libj.lang.ObjectUtil.equals(lang, that.lang))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = -875109195;
      if (lang != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(lang);

      return hashCode;
    }

    @java.lang.Override
    public java.lang.String toString() {
      return org.jsonx.JxEncoder.get().toString(this);
    }
  }

  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/binding-0.4.jsdx")
  public static class Number extends org.jsonx.binding.TypeFieldBindings {
    @org.jsonx.StringProperty(name="jx:type", pattern="number", nullable=false)
    public java.lang.String getJx3aType() {
      return jx3aType;
    }

    public Number setJx3aType(final java.lang.String jx3aType) {
      this.jx3aType = jx3aType;
      return this;
    }

    private java.lang.String jx3aType;

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.binding.Number) || !super.equals(obj))
        return false;

      final org.jsonx.binding.Number that = (org.jsonx.binding.Number)obj;
      if (!org.libj.lang.ObjectUtil.equals(jx3aType, that.jx3aType))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 909350992 * 31 + super.hashCode();
      if (jx3aType != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(jx3aType);

      return hashCode;
    }
  }

  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/binding-0.4.jsdx")
  public static class Object extends org.jsonx.binding.FieldBindings {
    @org.jsonx.StringProperty(name="jx:type", pattern="object", nullable=false)
    public java.lang.String getJx3aType() {
      return jx3aType;
    }

    public Object setJx3aType(final java.lang.String jx3aType) {
      this.jx3aType = jx3aType;
      return this;
    }

    private java.lang.String jx3aType;

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.binding.Object) || !super.equals(obj))
        return false;

      final org.jsonx.binding.Object that = (org.jsonx.binding.Object)obj;
      if (!org.libj.lang.ObjectUtil.equals(jx3aType, that.jx3aType))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 920346694 * 31 + super.hashCode();
      if (jx3aType != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(jx3aType);

      return hashCode;
    }
  }

  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/binding-0.4.jsdx")
  public abstract static class Path implements org.jsonx.JxObject {
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.binding.Path))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = -874989844;
      return hashCode;
    }

    @java.lang.Override
    public java.lang.String toString() {
      return org.jsonx.JxEncoder.get().toString(this);
    }
  }

  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/binding-0.4.jsdx")
  public static class Reference extends org.jsonx.binding.FieldBindings {
    @org.jsonx.StringProperty(name="jx:type", pattern="reference", nullable=false)
    public java.lang.String getJx3aType() {
      return jx3aType;
    }

    public Reference setJx3aType(final java.lang.String jx3aType) {
      this.jx3aType = jx3aType;
      return this;
    }

    private java.lang.String jx3aType;

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.binding.Reference) || !super.equals(obj))
        return false;

      final org.jsonx.binding.Reference that = (org.jsonx.binding.Reference)obj;
      if (!org.libj.lang.ObjectUtil.equals(jx3aType, that.jx3aType))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = -458321692 * 31 + super.hashCode();
      if (jx3aType != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(jx3aType);

      return hashCode;
    }
  }

  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/binding-0.4.jsdx")
  public static class String extends org.jsonx.binding.TypeFieldBindings {
    @org.jsonx.StringProperty(name="jx:type", pattern="string", nullable=false)
    public java.lang.String getJx3aType() {
      return jx3aType;
    }

    public String setJx3aType(final java.lang.String jx3aType) {
      this.jx3aType = jx3aType;
      return this;
    }

    private java.lang.String jx3aType;

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.binding.String) || !super.equals(obj))
        return false;

      final org.jsonx.binding.String that = (org.jsonx.binding.String)obj;
      if (!org.libj.lang.ObjectUtil.equals(jx3aType, that.jx3aType))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 1051729176 * 31 + super.hashCode();
      if (jx3aType != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(jx3aType);

      return hashCode;
    }
  }

  /** Specifies language-specific binding. **/
  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/binding-0.4.jsdx")
  public static class TypeBinding extends org.jsonx.binding.Lang {
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

      if (!(obj instanceof org.jsonx.binding.TypeBinding) || !super.equals(obj))
        return false;

      final org.jsonx.binding.TypeBinding that = (org.jsonx.binding.TypeBinding)obj;
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
      int hashCode = -1725345468 * 31 + super.hashCode();
      if (type != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(type);

      if (decode != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(decode);

      if (encode != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(encode);

      return hashCode;
    }
  }

  /** Specifies language-specific binding. **/
  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/binding-0.4.jsdx")
  public static class TypeFieldBinding extends org.jsonx.binding.TypeBinding {
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

      if (!(obj instanceof org.jsonx.binding.TypeFieldBinding) || !super.equals(obj))
        return false;

      final org.jsonx.binding.TypeFieldBinding that = (org.jsonx.binding.TypeFieldBinding)obj;
      if (!org.libj.lang.ObjectUtil.equals(field, that.field))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 2047784140 * 31 + super.hashCode();
      if (field != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(field);

      return hashCode;
    }
  }

  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/binding-0.4.jsdx")
  public abstract static class TypeFieldBindings extends org.jsonx.binding.Path {
    @org.jsonx.ObjectElement(id=0, type=org.jsonx.binding.TypeFieldBinding.class, nullable=false)
    @org.jsonx.ArrayProperty(name="bindings", elementIds={0}, nullable=false)
    public java.util.List<org.jsonx.binding.TypeFieldBinding> getBindings() {
      return bindings;
    }

    public TypeFieldBindings setBindings(final java.util.List<org.jsonx.binding.TypeFieldBinding> bindings) {
      this.bindings = bindings;
      return this;
    }

    private java.util.List<org.jsonx.binding.TypeFieldBinding> bindings;

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.binding.TypeFieldBindings) || !super.equals(obj))
        return false;

      final org.jsonx.binding.TypeFieldBindings that = (org.jsonx.binding.TypeFieldBindings)obj;
      if (!org.libj.lang.ObjectUtil.equals(bindings, that.bindings))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = -943200985 * 31 + super.hashCode();
      if (bindings != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(bindings);

      return hashCode;
    }
  }
}