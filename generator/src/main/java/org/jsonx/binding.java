package org.jsonx;

@java.lang.SuppressWarnings("all")
@javax.annotation.Generated(value="org.jsonx.Generator", date="2023-09-12T12:16:24.632")
public class binding {
  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/binding-0.4.jsdx")
  public static class Any extends org.jsonx.binding.FieldBindings {
    @org.jsonx.StringProperty(name="@", pattern="any", nullable=false)
    public java.lang.String get40() {
      return _40;
    }

    public Any set40(final java.lang.String _40) {
      this._40 = _40;
      return this;
    }

    private java.lang.String _40;

    public Any set5cS7c5cS2e2a5cS(final java.util.LinkedHashMap<java.lang.String,org.jsonx.binding.FieldBinding> _5cS7c5cS2e2a5cS) {
      super.set5cS7c5cS2e2a5cS(_5cS7c5cS2e2a5cS);
      return (Any)this;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.binding.Any) || !super.equals(obj))
        return false;

      final org.jsonx.binding.Any that = (org.jsonx.binding.Any)obj;
      if (!org.libj.lang.ObjectUtil.equals(_40, that._40))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = -1967902139 * 31 + super.hashCode();
      if (_40 != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40);

      return hashCode;
    }
  }

  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/binding-0.4.jsdx")
  public static class Array extends org.jsonx.binding.FieldBindings {
    @org.jsonx.StringProperty(name="@", pattern="array", nullable=false)
    public java.lang.String get40() {
      return _40;
    }

    public Array set40(final java.lang.String _40) {
      this._40 = _40;
      return this;
    }

    private java.lang.String _40;

    public Array set5cS7c5cS2e2a5cS(final java.util.LinkedHashMap<java.lang.String,org.jsonx.binding.FieldBinding> _5cS7c5cS2e2a5cS) {
      super.set5cS7c5cS2e2a5cS(_5cS7c5cS2e2a5cS);
      return (Array)this;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.binding.Array) || !super.equals(obj))
        return false;

      final org.jsonx.binding.Array that = (org.jsonx.binding.Array)obj;
      if (!org.libj.lang.ObjectUtil.equals(_40, that._40))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = -1368229774 * 31 + super.hashCode();
      if (_40 != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40);

      return hashCode;
    }
  }

  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/binding-0.4.jsdx")
  public static class Binding implements org.jsonx.JxObject {
    @org.jsonx.StringProperty(name="@ns", pattern="http://www.jsonx.org/binding-0.4.jsd", nullable=false)
    public java.lang.String get40ns() {
      return _40ns;
    }

    public Binding set40ns(final java.lang.String _40ns) {
      this._40ns = _40ns;
      return this;
    }

    private java.lang.String _40ns;

    @org.jsonx.StringProperty(name="@schemaLocation", pattern="((\\S|\\S.*\\S) (\\S|\\S.*\\S))+", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String get40schemaLocation() {
      return _40schemaLocation;
    }

    public Binding set40schemaLocation(final java.lang.String _40schemaLocation) {
      this._40schemaLocation = _40schemaLocation;
      return this;
    }

    private java.lang.String _40schemaLocation;

    @org.jsonx.AnyProperty(name="@schema", types={@org.jsonx.t(objects=org.jsonx.schema.Schema.class), @org.jsonx.t(objects=org.jsonx.include.Include.class)}, nullable=false)
    public org.jsonx.JxObject get40schema() {
      return _40schema;
    }

    public Binding set40schema(final org.jsonx.JxObject _40schema) {
      this._40schema = _40schema;
      return this;
    }

    private org.jsonx.JxObject _40schema;

    @org.jsonx.AnyProperty(name="\\S|\\S.*\\S", types={@org.jsonx.t(objects=org.jsonx.binding.Any.class), @org.jsonx.t(objects=org.jsonx.binding.Reference.class), @org.jsonx.t(objects=org.jsonx.binding.Array.class), @org.jsonx.t(objects=org.jsonx.binding.Object.class), @org.jsonx.t(objects=org.jsonx.binding.Boolean.class), @org.jsonx.t(objects=org.jsonx.binding.Number.class), @org.jsonx.t(objects=org.jsonx.binding.String.class)}, nullable=false)
    public java.util.LinkedHashMap<java.lang.String,org.jsonx.JxObject> get5cS7c5cS2e2a5cS() {
      return _5cS7c5cS2e2a5cS;
    }

    public Binding set5cS7c5cS2e2a5cS(final java.util.LinkedHashMap<java.lang.String,org.jsonx.JxObject> _5cS7c5cS2e2a5cS) {
      this._5cS7c5cS2e2a5cS = _5cS7c5cS2e2a5cS;
      return this;
    }

    private java.util.LinkedHashMap<java.lang.String,org.jsonx.JxObject> _5cS7c5cS2e2a5cS;

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.binding.Binding))
        return false;

      final org.jsonx.binding.Binding that = (org.jsonx.binding.Binding)obj;
      if (!org.libj.lang.ObjectUtil.equals(_40ns, that._40ns))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(_40schemaLocation, that._40schemaLocation))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(_40schema, that._40schema))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(_5cS7c5cS2e2a5cS, that._5cS7c5cS2e2a5cS))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 17404510;
      if (_40ns != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40ns);

      if (_40schemaLocation != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40schemaLocation);

      if (_40schema != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40schema);

      if (_5cS7c5cS2e2a5cS != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_5cS7c5cS2e2a5cS);

      return hashCode;
    }

    @java.lang.Override
    public java.lang.String toString() {
      return org.jsonx.JxEncoder.get().toString(this);
    }
  }

  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/binding-0.4.jsdx")
  public static class Boolean extends org.jsonx.binding.TypeFieldBindings {
    @org.jsonx.StringProperty(name="@", pattern="boolean", nullable=false)
    public java.lang.String get40() {
      return _40;
    }

    public Boolean set40(final java.lang.String _40) {
      this._40 = _40;
      return this;
    }

    private java.lang.String _40;

    public Boolean set5cS7c5cS2e2a5cS(final java.util.LinkedHashMap<java.lang.String,org.jsonx.binding.TypeFieldBinding> _5cS7c5cS2e2a5cS) {
      super.set5cS7c5cS2e2a5cS(_5cS7c5cS2e2a5cS);
      return (Boolean)this;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.binding.Boolean) || !super.equals(obj))
        return false;

      final org.jsonx.binding.Boolean that = (org.jsonx.binding.Boolean)obj;
      if (!org.libj.lang.ObjectUtil.equals(_40, that._40))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 190337025 * 31 + super.hashCode();
      if (_40 != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40);

      return hashCode;
    }
  }

  /** Specifies language-specific binding. **/
  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/binding-0.4.jsdx")
  public static class FieldBinding implements org.jsonx.JxObject {
    /** Specifies the "field" identifier. **/
    @org.jsonx.StringProperty(name="@field", pattern="[a-zA-Z_$][a-zA-Z\\d_$]*", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String get40field() {
      return _40field;
    }

    /** Specifies the "field" identifier. **/
    public FieldBinding set40field(final java.lang.String _40field) {
      this._40field = _40field;
      return this;
    }

    private java.lang.String _40field;

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.binding.FieldBinding))
        return false;

      final org.jsonx.binding.FieldBinding that = (org.jsonx.binding.FieldBinding)obj;
      if (!org.libj.lang.ObjectUtil.equals(_40field, that._40field))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 361567218;
      if (_40field != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40field);

      return hashCode;
    }

    @java.lang.Override
    public java.lang.String toString() {
      return org.jsonx.JxEncoder.get().toString(this);
    }
  }

  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/binding-0.4.jsdx")
  public abstract static class FieldBindings implements org.jsonx.JxObject {
    @org.jsonx.AnyProperty(name="\\S|\\S.*\\S", types=@org.jsonx.t(objects=org.jsonx.binding.FieldBinding.class), nullable=false)
    public java.util.LinkedHashMap<java.lang.String,org.jsonx.binding.FieldBinding> get5cS7c5cS2e2a5cS() {
      return _5cS7c5cS2e2a5cS;
    }

    public FieldBindings set5cS7c5cS2e2a5cS(final java.util.LinkedHashMap<java.lang.String,org.jsonx.binding.FieldBinding> _5cS7c5cS2e2a5cS) {
      this._5cS7c5cS2e2a5cS = _5cS7c5cS2e2a5cS;
      return this;
    }

    private java.util.LinkedHashMap<java.lang.String,org.jsonx.binding.FieldBinding> _5cS7c5cS2e2a5cS;

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.binding.FieldBindings))
        return false;

      final org.jsonx.binding.FieldBindings that = (org.jsonx.binding.FieldBindings)obj;
      if (!org.libj.lang.ObjectUtil.equals(_5cS7c5cS2e2a5cS, that._5cS7c5cS2e2a5cS))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = -1676318015;
      if (_5cS7c5cS2e2a5cS != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_5cS7c5cS2e2a5cS);

      return hashCode;
    }

    @java.lang.Override
    public java.lang.String toString() {
      return org.jsonx.JxEncoder.get().toString(this);
    }
  }

  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/binding-0.4.jsdx")
  public static class Number extends org.jsonx.binding.TypeFieldBindings {
    @org.jsonx.StringProperty(name="@", pattern="number", nullable=false)
    public java.lang.String get40() {
      return _40;
    }

    public Number set40(final java.lang.String _40) {
      this._40 = _40;
      return this;
    }

    private java.lang.String _40;

    public Number set5cS7c5cS2e2a5cS(final java.util.LinkedHashMap<java.lang.String,org.jsonx.binding.TypeFieldBinding> _5cS7c5cS2e2a5cS) {
      super.set5cS7c5cS2e2a5cS(_5cS7c5cS2e2a5cS);
      return (Number)this;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.binding.Number) || !super.equals(obj))
        return false;

      final org.jsonx.binding.Number that = (org.jsonx.binding.Number)obj;
      if (!org.libj.lang.ObjectUtil.equals(_40, that._40))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 909350992 * 31 + super.hashCode();
      if (_40 != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40);

      return hashCode;
    }
  }

  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/binding-0.4.jsdx")
  public static class Object extends org.jsonx.binding.FieldBindings {
    @org.jsonx.StringProperty(name="@", pattern="object", nullable=false)
    public java.lang.String get40() {
      return _40;
    }

    public Object set40(final java.lang.String _40) {
      this._40 = _40;
      return this;
    }

    private java.lang.String _40;

    public Object set5cS7c5cS2e2a5cS(final java.util.LinkedHashMap<java.lang.String,org.jsonx.binding.FieldBinding> _5cS7c5cS2e2a5cS) {
      super.set5cS7c5cS2e2a5cS(_5cS7c5cS2e2a5cS);
      return (Object)this;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.binding.Object) || !super.equals(obj))
        return false;

      final org.jsonx.binding.Object that = (org.jsonx.binding.Object)obj;
      if (!org.libj.lang.ObjectUtil.equals(_40, that._40))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 920346694 * 31 + super.hashCode();
      if (_40 != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40);

      return hashCode;
    }
  }

  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/binding-0.4.jsdx")
  public static class Reference extends org.jsonx.binding.FieldBindings {
    @org.jsonx.StringProperty(name="@", pattern="reference", nullable=false)
    public java.lang.String get40() {
      return _40;
    }

    public Reference set40(final java.lang.String _40) {
      this._40 = _40;
      return this;
    }

    private java.lang.String _40;

    public Reference set5cS7c5cS2e2a5cS(final java.util.LinkedHashMap<java.lang.String,org.jsonx.binding.FieldBinding> _5cS7c5cS2e2a5cS) {
      super.set5cS7c5cS2e2a5cS(_5cS7c5cS2e2a5cS);
      return (Reference)this;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.binding.Reference) || !super.equals(obj))
        return false;

      final org.jsonx.binding.Reference that = (org.jsonx.binding.Reference)obj;
      if (!org.libj.lang.ObjectUtil.equals(_40, that._40))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = -458321692 * 31 + super.hashCode();
      if (_40 != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40);

      return hashCode;
    }
  }

  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/binding-0.4.jsdx")
  public static class String extends org.jsonx.binding.TypeFieldBindings {
    @org.jsonx.StringProperty(name="@", pattern="string", nullable=false)
    public java.lang.String get40() {
      return _40;
    }

    public String set40(final java.lang.String _40) {
      this._40 = _40;
      return this;
    }

    private java.lang.String _40;

    public String set5cS7c5cS2e2a5cS(final java.util.LinkedHashMap<java.lang.String,org.jsonx.binding.TypeFieldBinding> _5cS7c5cS2e2a5cS) {
      super.set5cS7c5cS2e2a5cS(_5cS7c5cS2e2a5cS);
      return (String)this;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.binding.String) || !super.equals(obj))
        return false;

      final org.jsonx.binding.String that = (org.jsonx.binding.String)obj;
      if (!org.libj.lang.ObjectUtil.equals(_40, that._40))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 1051729176 * 31 + super.hashCode();
      if (_40 != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40);

      return hashCode;
    }
  }

  /** Specifies language-specific binding. **/
  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/binding-0.4.jsdx")
  public static class TypeBinding implements org.jsonx.JxObject {
    /** Specifies the "type" qualified identifier. **/
    @org.jsonx.StringProperty(name="@type", pattern="(([a-zA-Z_$][a-zA-Z\\d_$]*)\\.)*[a-zA-Z_$][a-zA-Z\\d_$]*(\\[\\])?", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String get40type() {
      return _40type;
    }

    /** Specifies the "type" qualified identifier. **/
    public TypeBinding set40type(final java.lang.String _40type) {
      this._40type = _40type;
      return this;
    }

    private java.lang.String _40type;

    /** Specifies the "decode" qualified function identifier that accepts input as a string, or as the native JSON type of this property, and returns an output of the specified "type" (or the default type if "type" is unspecified). **/
    @org.jsonx.StringProperty(name="@decode", pattern="(([a-zA-Z_$][a-zA-Z\\d_$]*)\\.)*[a-zA-Z_$][a-zA-Z\\d_$]*(\\.<init>)?", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String get40decode() {
      return _40decode;
    }

    /** Specifies the "decode" qualified function identifier that accepts input as a string, or as the native JSON type of this property, and returns an output of the specified "type" (or the default type if "type" is unspecified). **/
    public TypeBinding set40decode(final java.lang.String _40decode) {
      this._40decode = _40decode;
      return this;
    }

    private java.lang.String _40decode;

    /** Specifies the "encode" qualified function identifier that accepts input of the type specified in "type" (or the default type if "type" is unspecified), and returns an output as a string, or as the native JSON type of this property. **/
    @org.jsonx.StringProperty(name="@encode", pattern="(([a-zA-Z_$][a-zA-Z\\d_$]*)\\.)*[a-zA-Z_$][a-zA-Z\\d_$]*(\\.<init>)?", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String get40encode() {
      return _40encode;
    }

    /** Specifies the "encode" qualified function identifier that accepts input of the type specified in "type" (or the default type if "type" is unspecified), and returns an output as a string, or as the native JSON type of this property. **/
    public TypeBinding set40encode(final java.lang.String _40encode) {
      this._40encode = _40encode;
      return this;
    }

    private java.lang.String _40encode;

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.binding.TypeBinding))
        return false;

      final org.jsonx.binding.TypeBinding that = (org.jsonx.binding.TypeBinding)obj;
      if (!org.libj.lang.ObjectUtil.equals(_40type, that._40type))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(_40decode, that._40decode))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(_40encode, that._40encode))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = -1725345468;
      if (_40type != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40type);

      if (_40decode != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40decode);

      if (_40encode != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40encode);

      return hashCode;
    }

    @java.lang.Override
    public java.lang.String toString() {
      return org.jsonx.JxEncoder.get().toString(this);
    }
  }

  /** Specifies language-specific binding. **/
  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/binding-0.4.jsdx")
  public static class TypeFieldBinding extends org.jsonx.binding.TypeBinding {
    /** Specifies the "field" identifier. **/
    @org.jsonx.StringProperty(name="@field", pattern="[a-zA-Z_$][a-zA-Z\\d_$]*", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String get40field() {
      return _40field;
    }

    /** Specifies the "field" identifier. **/
    public TypeFieldBinding set40field(final java.lang.String _40field) {
      this._40field = _40field;
      return this;
    }

    private java.lang.String _40field;

    /** Specifies the "type" qualified identifier. **/
    public TypeFieldBinding set40type(final java.lang.String _40type) {
      super.set40type(_40type);
      return (TypeFieldBinding)this;
    }

    /** Specifies the "decode" qualified function identifier that accepts input as a string, or as the native JSON type of this property, and returns an output of the specified "type" (or the default type if "type" is unspecified). **/
    public TypeFieldBinding set40decode(final java.lang.String _40decode) {
      super.set40decode(_40decode);
      return (TypeFieldBinding)this;
    }

    /** Specifies the "encode" qualified function identifier that accepts input of the type specified in "type" (or the default type if "type" is unspecified), and returns an output as a string, or as the native JSON type of this property. **/
    public TypeFieldBinding set40encode(final java.lang.String _40encode) {
      super.set40encode(_40encode);
      return (TypeFieldBinding)this;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.binding.TypeFieldBinding) || !super.equals(obj))
        return false;

      final org.jsonx.binding.TypeFieldBinding that = (org.jsonx.binding.TypeFieldBinding)obj;
      if (!org.libj.lang.ObjectUtil.equals(_40field, that._40field))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 2047784140 * 31 + super.hashCode();
      if (_40field != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40field);

      return hashCode;
    }
  }

  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/binding-0.4.jsdx")
  public abstract static class TypeFieldBindings implements org.jsonx.JxObject {
    @org.jsonx.AnyProperty(name="\\S|\\S.*\\S", types=@org.jsonx.t(objects=org.jsonx.binding.TypeFieldBinding.class), nullable=false)
    public java.util.LinkedHashMap<java.lang.String,org.jsonx.binding.TypeFieldBinding> get5cS7c5cS2e2a5cS() {
      return _5cS7c5cS2e2a5cS;
    }

    public TypeFieldBindings set5cS7c5cS2e2a5cS(final java.util.LinkedHashMap<java.lang.String,org.jsonx.binding.TypeFieldBinding> _5cS7c5cS2e2a5cS) {
      this._5cS7c5cS2e2a5cS = _5cS7c5cS2e2a5cS;
      return this;
    }

    private java.util.LinkedHashMap<java.lang.String,org.jsonx.binding.TypeFieldBinding> _5cS7c5cS2e2a5cS;

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.binding.TypeFieldBindings))
        return false;

      final org.jsonx.binding.TypeFieldBindings that = (org.jsonx.binding.TypeFieldBindings)obj;
      if (!org.libj.lang.ObjectUtil.equals(_5cS7c5cS2e2a5cS, that._5cS7c5cS2e2a5cS))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = -943200985;
      if (_5cS7c5cS2e2a5cS != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_5cS7c5cS2e2a5cS);

      return hashCode;
    }

    @java.lang.Override
    public java.lang.String toString() {
      return org.jsonx.JxEncoder.get().toString(this);
    }
  }
}