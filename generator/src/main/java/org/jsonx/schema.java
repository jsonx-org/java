package org.jsonx;

@java.lang.SuppressWarnings("all")
@javax.annotation.Generated(value="org.jsonx.Generator", date="2023-09-09T18:25:34.214")
public class schema {
  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/schema-0.4.jsdx")
  public abstract static class Any extends org.jsonx.schema.Member {
    @org.jsonx.StringProperty(name="@", pattern="any", nullable=false)
    public java.lang.String get40() {
      return _40;
    }

    public Any set40(final java.lang.String _40) {
      this._40 = _40;
      return this;
    }

    private java.lang.String _40;

    @org.jsonx.StringProperty(name="@types", pattern="\\S|\\S.*\\S", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String get40types() {
      return _40types;
    }

    public Any set40types(final java.lang.String _40types) {
      this._40types = _40types;
      return this;
    }

    private java.lang.String _40types;

    /** Defines text comments. Optional. **/
    public Any set40doc(final java.lang.String _40doc) {
      super.set40doc(_40doc);
      return (Any)this;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.Any) || !super.equals(obj))
        return false;

      final org.jsonx.schema.Any that = (org.jsonx.schema.Any)obj;
      if (!org.libj.lang.ObjectUtil.equals(_40, that._40))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(_40types, that._40types))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 188459569 * 31 + super.hashCode();
      if (_40 != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40);

      if (_40types != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40types);

      return hashCode;
    }
  }

  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/schema-0.4.jsdx")
  public static class AnyElement extends org.jsonx.schema.Any {
    @org.jsonx.BooleanProperty(name="@nullable", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.Boolean get40nullable() {
      return _40nullable;
    }

    public AnyElement set40nullable(final java.lang.Boolean _40nullable) {
      this._40nullable = _40nullable;
      return this;
    }

    private java.lang.Boolean _40nullable;

    @org.jsonx.StringProperty(name="@minOccurs", pattern="\\d+", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String get40minOccurs() {
      return _40minOccurs;
    }

    public AnyElement set40minOccurs(final java.lang.String _40minOccurs) {
      this._40minOccurs = _40minOccurs;
      return this;
    }

    private java.lang.String _40minOccurs;

    @org.jsonx.StringProperty(name="@maxOccurs", pattern="\\d+|unbounded", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String get40maxOccurs() {
      return _40maxOccurs;
    }

    public AnyElement set40maxOccurs(final java.lang.String _40maxOccurs) {
      this._40maxOccurs = _40maxOccurs;
      return this;
    }

    private java.lang.String _40maxOccurs;

    public AnyElement set40(final java.lang.String _40) {
      super.set40(_40);
      return (AnyElement)this;
    }

    public AnyElement set40types(final java.lang.String _40types) {
      super.set40types(_40types);
      return (AnyElement)this;
    }

    /** Defines text comments. Optional. **/
    public AnyElement set40doc(final java.lang.String _40doc) {
      super.set40doc(_40doc);
      return (AnyElement)this;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.AnyElement) || !super.equals(obj))
        return false;

      final org.jsonx.schema.AnyElement that = (org.jsonx.schema.AnyElement)obj;
      if (!org.libj.lang.ObjectUtil.equals(_40nullable, that._40nullable))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(_40minOccurs, that._40minOccurs))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(_40maxOccurs, that._40maxOccurs))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 1449550731 * 31 + super.hashCode();
      if (_40nullable != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40nullable);

      if (_40minOccurs != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40minOccurs);

      if (_40maxOccurs != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40maxOccurs);

      return hashCode;
    }
  }

  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/schema-0.4.jsdx")
  public static class AnyProperty extends org.jsonx.schema.Any {
    @org.jsonx.BooleanProperty(name="@nullable", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.Boolean get40nullable() {
      return _40nullable;
    }

    public AnyProperty set40nullable(final java.lang.Boolean _40nullable) {
      this._40nullable = _40nullable;
      return this;
    }

    private java.lang.Boolean _40nullable;

    @org.jsonx.StringProperty(name="@use", pattern="required|optional", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String get40use() {
      return _40use;
    }

    public AnyProperty set40use(final java.lang.String _40use) {
      this._40use = _40use;
      return this;
    }

    private java.lang.String _40use;

    public AnyProperty set40(final java.lang.String _40) {
      super.set40(_40);
      return (AnyProperty)this;
    }

    public AnyProperty set40types(final java.lang.String _40types) {
      super.set40types(_40types);
      return (AnyProperty)this;
    }

    /** Defines text comments. Optional. **/
    public AnyProperty set40doc(final java.lang.String _40doc) {
      super.set40doc(_40doc);
      return (AnyProperty)this;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.AnyProperty) || !super.equals(obj))
        return false;

      final org.jsonx.schema.AnyProperty that = (org.jsonx.schema.AnyProperty)obj;
      if (!org.libj.lang.ObjectUtil.equals(_40nullable, that._40nullable))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(_40use, that._40use))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 1001597734 * 31 + super.hashCode();
      if (_40nullable != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40nullable);

      if (_40use != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40use);

      return hashCode;
    }
  }

  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/schema-0.4.jsdx")
  public static class Array extends org.jsonx.schema.Member {
    @org.jsonx.StringProperty(name="@", pattern="array", nullable=false)
    public java.lang.String get40() {
      return _40;
    }

    public Array set40(final java.lang.String _40) {
      this._40 = _40;
      return this;
    }

    private java.lang.String _40;

    /** Specifies the minimum inclusive number of iterations of child elements. Default: 1. **/
    @org.jsonx.StringProperty(name="@minIterate", pattern="\\d+", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String get40minIterate() {
      return _40minIterate;
    }

    /** Specifies the minimum inclusive number of iterations of child elements. Default: 1. **/
    public Array set40minIterate(final java.lang.String _40minIterate) {
      this._40minIterate = _40minIterate;
      return this;
    }

    private java.lang.String _40minIterate;

    /** Specifies the maximum inclusive number of iterations of child elements. Default: 1. **/
    @org.jsonx.StringProperty(name="@maxIterate", pattern="\\d+|unbounded", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String get40maxIterate() {
      return _40maxIterate;
    }

    /** Specifies the maximum inclusive number of iterations of child elements. Default: 1. **/
    public Array set40maxIterate(final java.lang.String _40maxIterate) {
      this._40maxIterate = _40maxIterate;
      return this;
    }

    private java.lang.String _40maxIterate;

    @org.jsonx.ObjectElement(id=6, type=org.jsonx.schema.ObjectElement.class, minOccurs=0, nullable=false)
    @org.jsonx.ObjectElement(id=5, type=org.jsonx.schema.StringElement.class, minOccurs=0, nullable=false)
    @org.jsonx.ObjectElement(id=4, type=org.jsonx.schema.ReferenceElement.class, minOccurs=0, nullable=false)
    @org.jsonx.ObjectElement(id=3, type=org.jsonx.schema.NumberElement.class, minOccurs=0, nullable=false)
    @org.jsonx.ObjectElement(id=2, type=org.jsonx.schema.BooleanElement.class, minOccurs=0, nullable=false)
    @org.jsonx.ObjectElement(id=1, type=org.jsonx.schema.ArrayElement.class, minOccurs=0, nullable=false)
    @org.jsonx.ObjectElement(id=0, type=org.jsonx.schema.AnyElement.class, minOccurs=0, nullable=false)
    @org.jsonx.ArrayProperty(name="@elements", elementIds={0, 1, 2, 3, 4, 5, 6}, maxIterate=2147483647, nullable=false)
    public java.util.List<org.jsonx.schema.Member> get40elements() {
      return _40elements;
    }

    public Array set40elements(final java.util.List<org.jsonx.schema.Member> _40elements) {
      this._40elements = _40elements;
      return this;
    }

    private java.util.List<org.jsonx.schema.Member> _40elements;

    /** Defines text comments. Optional. **/
    public Array set40doc(final java.lang.String _40doc) {
      super.set40doc(_40doc);
      return (Array)this;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.Array) || !super.equals(obj))
        return false;

      final org.jsonx.schema.Array that = (org.jsonx.schema.Array)obj;
      if (!org.libj.lang.ObjectUtil.equals(_40, that._40))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(_40minIterate, that._40minIterate))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(_40maxIterate, that._40maxIterate))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(_40elements, that._40elements))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 721134942 * 31 + super.hashCode();
      if (_40 != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40);

      if (_40minIterate != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40minIterate);

      if (_40maxIterate != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40maxIterate);

      if (_40elements != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40elements);

      return hashCode;
    }
  }

  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/schema-0.4.jsdx")
  public static class ArrayElement extends org.jsonx.schema.Array {
    @org.jsonx.BooleanProperty(name="@nullable", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.Boolean get40nullable() {
      return _40nullable;
    }

    public ArrayElement set40nullable(final java.lang.Boolean _40nullable) {
      this._40nullable = _40nullable;
      return this;
    }

    private java.lang.Boolean _40nullable;

    @org.jsonx.StringProperty(name="@minOccurs", pattern="\\d+", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String get40minOccurs() {
      return _40minOccurs;
    }

    public ArrayElement set40minOccurs(final java.lang.String _40minOccurs) {
      this._40minOccurs = _40minOccurs;
      return this;
    }

    private java.lang.String _40minOccurs;

    @org.jsonx.StringProperty(name="@maxOccurs", pattern="\\d+|unbounded", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String get40maxOccurs() {
      return _40maxOccurs;
    }

    public ArrayElement set40maxOccurs(final java.lang.String _40maxOccurs) {
      this._40maxOccurs = _40maxOccurs;
      return this;
    }

    private java.lang.String _40maxOccurs;

    public ArrayElement set40(final java.lang.String _40) {
      super.set40(_40);
      return (ArrayElement)this;
    }

    /** Specifies the minimum inclusive number of iterations of child elements. Default: 1. **/
    public ArrayElement set40minIterate(final java.lang.String _40minIterate) {
      super.set40minIterate(_40minIterate);
      return (ArrayElement)this;
    }

    /** Specifies the maximum inclusive number of iterations of child elements. Default: 1. **/
    public ArrayElement set40maxIterate(final java.lang.String _40maxIterate) {
      super.set40maxIterate(_40maxIterate);
      return (ArrayElement)this;
    }

    /** Defines text comments. Optional. **/
    public ArrayElement set40doc(final java.lang.String _40doc) {
      super.set40doc(_40doc);
      return (ArrayElement)this;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.ArrayElement) || !super.equals(obj))
        return false;

      final org.jsonx.schema.ArrayElement that = (org.jsonx.schema.ArrayElement)obj;
      if (!org.libj.lang.ObjectUtil.equals(_40nullable, that._40nullable))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(_40minOccurs, that._40minOccurs))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(_40maxOccurs, that._40maxOccurs))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = -614798402 * 31 + super.hashCode();
      if (_40nullable != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40nullable);

      if (_40minOccurs != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40minOccurs);

      if (_40maxOccurs != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40maxOccurs);

      return hashCode;
    }
  }

  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/schema-0.4.jsdx")
  public static class ArrayProperty extends org.jsonx.schema.Array {
    @org.jsonx.BooleanProperty(name="@nullable", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.Boolean get40nullable() {
      return _40nullable;
    }

    public ArrayProperty set40nullable(final java.lang.Boolean _40nullable) {
      this._40nullable = _40nullable;
      return this;
    }

    private java.lang.Boolean _40nullable;

    @org.jsonx.StringProperty(name="@use", pattern="required|optional", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String get40use() {
      return _40use;
    }

    public ArrayProperty set40use(final java.lang.String _40use) {
      this._40use = _40use;
      return this;
    }

    private java.lang.String _40use;

    public ArrayProperty set40(final java.lang.String _40) {
      super.set40(_40);
      return (ArrayProperty)this;
    }

    /** Specifies the minimum inclusive number of iterations of child elements. Default: 1. **/
    public ArrayProperty set40minIterate(final java.lang.String _40minIterate) {
      super.set40minIterate(_40minIterate);
      return (ArrayProperty)this;
    }

    /** Specifies the maximum inclusive number of iterations of child elements. Default: 1. **/
    public ArrayProperty set40maxIterate(final java.lang.String _40maxIterate) {
      super.set40maxIterate(_40maxIterate);
      return (ArrayProperty)this;
    }

    /** Defines text comments. Optional. **/
    public ArrayProperty set40doc(final java.lang.String _40doc) {
      super.set40doc(_40doc);
      return (ArrayProperty)this;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.ArrayProperty) || !super.equals(obj))
        return false;

      final org.jsonx.schema.ArrayProperty that = (org.jsonx.schema.ArrayProperty)obj;
      if (!org.libj.lang.ObjectUtil.equals(_40nullable, that._40nullable))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(_40use, that._40use))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 1431284051 * 31 + super.hashCode();
      if (_40nullable != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40nullable);

      if (_40use != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40use);

      return hashCode;
    }
  }

  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/schema-0.4.jsdx")
  public static class Boolean extends org.jsonx.schema.Member {
    @org.jsonx.StringProperty(name="@", pattern="boolean", nullable=false)
    public java.lang.String get40() {
      return _40;
    }

    public Boolean set40(final java.lang.String _40) {
      this._40 = _40;
      return this;
    }

    private java.lang.String _40;

    /** Defines text comments. Optional. **/
    public Boolean set40doc(final java.lang.String _40doc) {
      super.set40doc(_40doc);
      return (Boolean)this;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.Boolean) || !super.equals(obj))
        return false;

      final org.jsonx.schema.Boolean that = (org.jsonx.schema.Boolean)obj;
      if (!org.libj.lang.ObjectUtil.equals(_40, that._40))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = -1974865427 * 31 + super.hashCode();
      if (_40 != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40);

      return hashCode;
    }
  }

  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/schema-0.4.jsdx")
  public static class BooleanElement extends org.jsonx.schema.Boolean {
    @org.jsonx.BooleanProperty(name="@nullable", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.Boolean get40nullable() {
      return _40nullable;
    }

    public BooleanElement set40nullable(final java.lang.Boolean _40nullable) {
      this._40nullable = _40nullable;
      return this;
    }

    private java.lang.Boolean _40nullable;

    @org.jsonx.StringProperty(name="@minOccurs", pattern="\\d+", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String get40minOccurs() {
      return _40minOccurs;
    }

    public BooleanElement set40minOccurs(final java.lang.String _40minOccurs) {
      this._40minOccurs = _40minOccurs;
      return this;
    }

    private java.lang.String _40minOccurs;

    @org.jsonx.StringProperty(name="@maxOccurs", pattern="\\d+|unbounded", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String get40maxOccurs() {
      return _40maxOccurs;
    }

    public BooleanElement set40maxOccurs(final java.lang.String _40maxOccurs) {
      this._40maxOccurs = _40maxOccurs;
      return this;
    }

    private java.lang.String _40maxOccurs;

    public BooleanElement set40(final java.lang.String _40) {
      super.set40(_40);
      return (BooleanElement)this;
    }

    /** Defines text comments. Optional. **/
    public BooleanElement set40doc(final java.lang.String _40doc) {
      super.set40doc(_40doc);
      return (BooleanElement)this;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.BooleanElement) || !super.equals(obj))
        return false;

      final org.jsonx.schema.BooleanElement that = (org.jsonx.schema.BooleanElement)obj;
      if (!org.libj.lang.ObjectUtil.equals(_40nullable, that._40nullable))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(_40minOccurs, that._40minOccurs))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(_40maxOccurs, that._40maxOccurs))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 677851215 * 31 + super.hashCode();
      if (_40nullable != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40nullable);

      if (_40minOccurs != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40minOccurs);

      if (_40maxOccurs != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40maxOccurs);

      return hashCode;
    }
  }

  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/schema-0.4.jsdx")
  public static class BooleanProperty extends org.jsonx.schema.Boolean {
    @org.jsonx.BooleanProperty(name="@nullable", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.Boolean get40nullable() {
      return _40nullable;
    }

    public BooleanProperty set40nullable(final java.lang.Boolean _40nullable) {
      this._40nullable = _40nullable;
      return this;
    }

    private java.lang.Boolean _40nullable;

    @org.jsonx.StringProperty(name="@use", pattern="required|optional", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String get40use() {
      return _40use;
    }

    public BooleanProperty set40use(final java.lang.String _40use) {
      this._40use = _40use;
      return this;
    }

    private java.lang.String _40use;

    public BooleanProperty set40(final java.lang.String _40) {
      super.set40(_40);
      return (BooleanProperty)this;
    }

    /** Defines text comments. Optional. **/
    public BooleanProperty set40doc(final java.lang.String _40doc) {
      super.set40doc(_40doc);
      return (BooleanProperty)this;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.BooleanProperty) || !super.equals(obj))
        return false;

      final org.jsonx.schema.BooleanProperty that = (org.jsonx.schema.BooleanProperty)obj;
      if (!org.libj.lang.ObjectUtil.equals(_40nullable, that._40nullable))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(_40use, that._40use))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = -1446250782 * 31 + super.hashCode();
      if (_40nullable != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40nullable);

      if (_40use != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40use);

      return hashCode;
    }
  }

  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/schema-0.4.jsdx")
  public abstract static class Documented implements org.jsonx.JxObject {
    /** Defines text comments. Optional. **/
    @org.jsonx.StringProperty(name="@doc")
    public java.lang.String get40doc() {
      return _40doc;
    }

    /** Defines text comments. Optional. **/
    public Documented set40doc(final java.lang.String _40doc) {
      this._40doc = _40doc;
      return this;
    }

    private java.lang.String _40doc;

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.Documented))
        return false;

      final org.jsonx.schema.Documented that = (org.jsonx.schema.Documented)obj;
      if (!org.libj.lang.ObjectUtil.equals(_40doc, that._40doc))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = -955389163;
      if (_40doc != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40doc);

      return hashCode;
    }

    @java.lang.Override
    public java.lang.String toString() {
      return org.jsonx.JxEncoder.get().toString(this);
    }
  }

  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/schema-0.4.jsdx")
  public static class Import implements org.jsonx.JxObject {
    @org.jsonx.StringProperty(name="@namespace", pattern="\\S|\\S.*\\S", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String get40namespace() {
      return _40namespace;
    }

    public Import set40namespace(final java.lang.String _40namespace) {
      this._40namespace = _40namespace;
      return this;
    }

    private java.lang.String _40namespace;

    @org.jsonx.StringProperty(name="@schemaLocation", pattern="((\\S|\\S.*\\S) (\\S|\\S.*\\S))+", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String get40schemaLocation() {
      return _40schemaLocation;
    }

    public Import set40schemaLocation(final java.lang.String _40schemaLocation) {
      this._40schemaLocation = _40schemaLocation;
      return this;
    }

    private java.lang.String _40schemaLocation;

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.Import))
        return false;

      final org.jsonx.schema.Import that = (org.jsonx.schema.Import)obj;
      if (!org.libj.lang.ObjectUtil.equals(_40namespace, that._40namespace))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(_40schemaLocation, that._40schemaLocation))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 1104716096;
      if (_40namespace != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40namespace);

      if (_40schemaLocation != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40schemaLocation);

      return hashCode;
    }

    @java.lang.Override
    public java.lang.String toString() {
      return org.jsonx.JxEncoder.get().toString(this);
    }
  }

  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/schema-0.4.jsdx")
  public abstract static class Member extends org.jsonx.schema.Documented {
    /** Defines text comments. Optional. **/
    public Member set40doc(final java.lang.String _40doc) {
      super.set40doc(_40doc);
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

  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/schema-0.4.jsdx")
  public static class Number extends org.jsonx.schema.Member {
    @org.jsonx.StringProperty(name="@", pattern="number", nullable=false)
    public java.lang.String get40() {
      return _40;
    }

    public Number set40(final java.lang.String _40) {
      this._40 = _40;
      return this;
    }

    private java.lang.String _40;

    /** Specifies the value range in interval notation:
           Open (exclusive) interval: (min,max)
           Closed (inclusive) interval: [min,max]
           Half-open or half-closed interval: [min,max)
           Degenerate interval: [val] **/
    @org.jsonx.StringProperty(name="@range", pattern="[\\(\\[](-?(0|[1-9]\\d*)(\\.\\d+)?([eE][+-]?([1-9]\\d*))?)?,(-?(0|[1-9]\\d*)(\\.\\d+)?([eE][+-]?([1-9]\\d*))?)?[\\)\\]]", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String get40range() {
      return _40range;
    }

    /** Specifies the value range in interval notation:
           Open (exclusive) interval: (min,max)
           Closed (inclusive) interval: [min,max]
           Half-open or half-closed interval: [min,max)
           Degenerate interval: [val] **/
    public Number set40range(final java.lang.String _40range) {
      this._40range = _40range;
      return this;
    }

    private java.lang.String _40range;

    /** The number of digits to the right of the decimal point. If a value is not specified, the scale is unbounded. **/
    @org.jsonx.NumberProperty(name="@scale", scale=0, use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.Long get40scale() {
      return _40scale;
    }

    /** The number of digits to the right of the decimal point. If a value is not specified, the scale is unbounded. **/
    public Number set40scale(final java.lang.Long _40scale) {
      this._40scale = _40scale;
      return this;
    }

    private java.lang.Long _40scale;

    /** Defines text comments. Optional. **/
    public Number set40doc(final java.lang.String _40doc) {
      super.set40doc(_40doc);
      return (Number)this;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.Number) || !super.equals(obj))
        return false;

      final org.jsonx.schema.Number that = (org.jsonx.schema.Number)obj;
      if (!org.libj.lang.ObjectUtil.equals(_40, that._40))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(_40range, that._40range))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(_40scale, that._40scale))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 1255147748 * 31 + super.hashCode();
      if (_40 != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40);

      if (_40range != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40range);

      if (_40scale != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40scale);

      return hashCode;
    }
  }

  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/schema-0.4.jsdx")
  public static class NumberElement extends org.jsonx.schema.Number {
    @org.jsonx.BooleanProperty(name="@nullable", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.Boolean get40nullable() {
      return _40nullable;
    }

    public NumberElement set40nullable(final java.lang.Boolean _40nullable) {
      this._40nullable = _40nullable;
      return this;
    }

    private java.lang.Boolean _40nullable;

    @org.jsonx.StringProperty(name="@minOccurs", pattern="\\d+", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String get40minOccurs() {
      return _40minOccurs;
    }

    public NumberElement set40minOccurs(final java.lang.String _40minOccurs) {
      this._40minOccurs = _40minOccurs;
      return this;
    }

    private java.lang.String _40minOccurs;

    @org.jsonx.StringProperty(name="@maxOccurs", pattern="\\d+|unbounded", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String get40maxOccurs() {
      return _40maxOccurs;
    }

    public NumberElement set40maxOccurs(final java.lang.String _40maxOccurs) {
      this._40maxOccurs = _40maxOccurs;
      return this;
    }

    private java.lang.String _40maxOccurs;

    public NumberElement set40(final java.lang.String _40) {
      super.set40(_40);
      return (NumberElement)this;
    }

    /** Specifies the value range in interval notation:
           Open (exclusive) interval: (min,max)
           Closed (inclusive) interval: [min,max]
           Half-open or half-closed interval: [min,max)
           Degenerate interval: [val] **/
    public NumberElement set40range(final java.lang.String _40range) {
      super.set40range(_40range);
      return (NumberElement)this;
    }

    /** The number of digits to the right of the decimal point. If a value is not specified, the scale is unbounded. **/
    public NumberElement set40scale(final java.lang.Long _40scale) {
      super.set40scale(_40scale);
      return (NumberElement)this;
    }

    /** Defines text comments. Optional. **/
    public NumberElement set40doc(final java.lang.String _40doc) {
      super.set40doc(_40doc);
      return (NumberElement)this;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.NumberElement) || !super.equals(obj))
        return false;

      final org.jsonx.schema.NumberElement that = (org.jsonx.schema.NumberElement)obj;
      if (!org.libj.lang.ObjectUtil.equals(_40nullable, that._40nullable))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(_40minOccurs, that._40minOccurs))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(_40maxOccurs, that._40maxOccurs))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 2034148728 * 31 + super.hashCode();
      if (_40nullable != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40nullable);

      if (_40minOccurs != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40minOccurs);

      if (_40maxOccurs != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40maxOccurs);

      return hashCode;
    }
  }

  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/schema-0.4.jsdx")
  public static class NumberProperty extends org.jsonx.schema.Number {
    @org.jsonx.BooleanProperty(name="@nullable", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.Boolean get40nullable() {
      return _40nullable;
    }

    public NumberProperty set40nullable(final java.lang.Boolean _40nullable) {
      this._40nullable = _40nullable;
      return this;
    }

    private java.lang.Boolean _40nullable;

    @org.jsonx.StringProperty(name="@use", pattern="required|optional", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String get40use() {
      return _40use;
    }

    public NumberProperty set40use(final java.lang.String _40use) {
      this._40use = _40use;
      return this;
    }

    private java.lang.String _40use;

    public NumberProperty set40(final java.lang.String _40) {
      super.set40(_40);
      return (NumberProperty)this;
    }

    /** Specifies the value range in interval notation:
           Open (exclusive) interval: (min,max)
           Closed (inclusive) interval: [min,max]
           Half-open or half-closed interval: [min,max)
           Degenerate interval: [val] **/
    public NumberProperty set40range(final java.lang.String _40range) {
      super.set40range(_40range);
      return (NumberProperty)this;
    }

    /** The number of digits to the right of the decimal point. If a value is not specified, the scale is unbounded. **/
    public NumberProperty set40scale(final java.lang.Long _40scale) {
      super.set40scale(_40scale);
      return (NumberProperty)this;
    }

    /** Defines text comments. Optional. **/
    public NumberProperty set40doc(final java.lang.String _40doc) {
      super.set40doc(_40doc);
      return (NumberProperty)this;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.NumberProperty) || !super.equals(obj))
        return false;

      final org.jsonx.schema.NumberProperty that = (org.jsonx.schema.NumberProperty)obj;
      if (!org.libj.lang.ObjectUtil.equals(_40nullable, that._40nullable))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(_40use, that._40use))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 1944266457 * 31 + super.hashCode();
      if (_40nullable != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40nullable);

      if (_40use != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40use);

      return hashCode;
    }
  }

  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/schema-0.4.jsdx")
  public abstract static class Object extends org.jsonx.schema.Member {
    @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/schema-0.4.jsdx")
    public static class _40properties implements org.jsonx.JxObject {
      @org.jsonx.AnyProperty(name=".*", types={@org.jsonx.t(objects=org.jsonx.schema.AnyProperty.class), @org.jsonx.t(objects=org.jsonx.schema.ArrayProperty.class), @org.jsonx.t(objects=org.jsonx.schema.BooleanProperty.class), @org.jsonx.t(objects=org.jsonx.schema.NumberProperty.class), @org.jsonx.t(objects=org.jsonx.schema.ObjectProperty.class), @org.jsonx.t(objects=org.jsonx.schema.ReferenceProperty.class), @org.jsonx.t(objects=org.jsonx.schema.StringProperty.class)}, nullable=false)
      public java.util.LinkedHashMap<java.lang.String,org.jsonx.schema.Member> get2e2a() {
        return _2e2a;
      }

      public _40properties set2e2a(final java.util.LinkedHashMap<java.lang.String,org.jsonx.schema.Member> _2e2a) {
        this._2e2a = _2e2a;
        return this;
      }

      private java.util.LinkedHashMap<java.lang.String,org.jsonx.schema.Member> _2e2a;

      @java.lang.Override
      public boolean equals(final java.lang.Object obj) {
        if (obj == this)
          return true;

        if (!(obj instanceof org.jsonx.schema.Object._40properties))
          return false;

        final org.jsonx.schema.Object._40properties that = (org.jsonx.schema.Object._40properties)obj;
        if (!org.libj.lang.ObjectUtil.equals(_2e2a, that._2e2a))
          return false;

        return true;
      }

      @java.lang.Override
      public int hashCode() {
        int hashCode = -1007110076;
        if (_2e2a != null)
          hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_2e2a);

        return hashCode;
      }

      @java.lang.Override
      public java.lang.String toString() {
        return org.jsonx.JxEncoder.get().toString(this);
      }
    }

    @org.jsonx.StringProperty(name="@", pattern="object", nullable=false)
    public java.lang.String get40() {
      return _40;
    }

    public Object set40(final java.lang.String _40) {
      this._40 = _40;
      return this;
    }

    private java.lang.String _40;

    /** Specifies the name of the type to extend. Optional. **/
    @org.jsonx.StringProperty(name="@extends", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String get40extends() {
      return _40extends;
    }

    /** Specifies the name of the type to extend. Optional. **/
    public Object set40extends(final java.lang.String _40extends) {
      this._40extends = _40extends;
      return this;
    }

    private java.lang.String _40extends;

    @org.jsonx.ObjectProperty(name="@properties", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public org.jsonx.schema.Object._40properties get40properties() {
      return _40properties;
    }

    public Object set40properties(final org.jsonx.schema.Object._40properties _40properties) {
      this._40properties = _40properties;
      return this;
    }

    private org.jsonx.schema.Object._40properties _40properties;

    /** Defines text comments. Optional. **/
    public Object set40doc(final java.lang.String _40doc) {
      super.set40doc(_40doc);
      return (Object)this;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.Object) || !super.equals(obj))
        return false;

      final org.jsonx.schema.Object that = (org.jsonx.schema.Object)obj;
      if (!org.libj.lang.ObjectUtil.equals(_40, that._40))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(_40extends, that._40extends))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(_40properties, that._40properties))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 1266143450 * 31 + super.hashCode();
      if (_40 != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40);

      if (_40extends != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40extends);

      if (_40properties != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40properties);

      return hashCode;
    }
  }

  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/schema-0.4.jsdx")
  public static class ObjectElement extends org.jsonx.schema.Object {
    @org.jsonx.BooleanProperty(name="@nullable", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.Boolean get40nullable() {
      return _40nullable;
    }

    public ObjectElement set40nullable(final java.lang.Boolean _40nullable) {
      this._40nullable = _40nullable;
      return this;
    }

    private java.lang.Boolean _40nullable;

    @org.jsonx.StringProperty(name="@minOccurs", pattern="\\d+", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String get40minOccurs() {
      return _40minOccurs;
    }

    public ObjectElement set40minOccurs(final java.lang.String _40minOccurs) {
      this._40minOccurs = _40minOccurs;
      return this;
    }

    private java.lang.String _40minOccurs;

    @org.jsonx.StringProperty(name="@maxOccurs", pattern="\\d+|unbounded", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String get40maxOccurs() {
      return _40maxOccurs;
    }

    public ObjectElement set40maxOccurs(final java.lang.String _40maxOccurs) {
      this._40maxOccurs = _40maxOccurs;
      return this;
    }

    private java.lang.String _40maxOccurs;

    public ObjectElement set40(final java.lang.String _40) {
      super.set40(_40);
      return (ObjectElement)this;
    }

    /** Specifies the name of the type to extend. Optional. **/
    public ObjectElement set40extends(final java.lang.String _40extends) {
      super.set40extends(_40extends);
      return (ObjectElement)this;
    }

    public ObjectElement set40properties(final org.jsonx.schema.Object._40properties _40properties) {
      super.set40properties(_40properties);
      return (ObjectElement)this;
    }

    /** Defines text comments. Optional. **/
    public ObjectElement set40doc(final java.lang.String _40doc) {
      super.set40doc(_40doc);
      return (ObjectElement)this;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.ObjectElement) || !super.equals(obj))
        return false;

      final org.jsonx.schema.ObjectElement that = (org.jsonx.schema.ObjectElement)obj;
      if (!org.libj.lang.ObjectUtil.equals(_40nullable, that._40nullable))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(_40minOccurs, that._40minOccurs))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(_40maxOccurs, that._40maxOccurs))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = -1694884670 * 31 + super.hashCode();
      if (_40nullable != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40nullable);

      if (_40minOccurs != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40minOccurs);

      if (_40maxOccurs != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40maxOccurs);

      return hashCode;
    }
  }

  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/schema-0.4.jsdx")
  public static class ObjectProperty extends org.jsonx.schema.Object {
    @org.jsonx.BooleanProperty(name="@nullable", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.Boolean get40nullable() {
      return _40nullable;
    }

    public ObjectProperty set40nullable(final java.lang.Boolean _40nullable) {
      this._40nullable = _40nullable;
      return this;
    }

    private java.lang.Boolean _40nullable;

    @org.jsonx.StringProperty(name="@use", pattern="required|optional", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String get40use() {
      return _40use;
    }

    public ObjectProperty set40use(final java.lang.String _40use) {
      this._40use = _40use;
      return this;
    }

    private java.lang.String _40use;

    public ObjectProperty set40(final java.lang.String _40) {
      super.set40(_40);
      return (ObjectProperty)this;
    }

    /** Specifies the name of the type to extend. Optional. **/
    public ObjectProperty set40extends(final java.lang.String _40extends) {
      super.set40extends(_40extends);
      return (ObjectProperty)this;
    }

    public ObjectProperty set40properties(final org.jsonx.schema.Object._40properties _40properties) {
      super.set40properties(_40properties);
      return (ObjectProperty)this;
    }

    /** Defines text comments. Optional. **/
    public ObjectProperty set40doc(final java.lang.String _40doc) {
      super.set40doc(_40doc);
      return (ObjectProperty)this;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.ObjectProperty) || !super.equals(obj))
        return false;

      final org.jsonx.schema.ObjectProperty that = (org.jsonx.schema.ObjectProperty)obj;
      if (!org.libj.lang.ObjectUtil.equals(_40nullable, that._40nullable))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(_40use, that._40use))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = -1986619185 * 31 + super.hashCode();
      if (_40nullable != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40nullable);

      if (_40use != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40use);

      return hashCode;
    }
  }

  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/schema-0.4.jsdx")
  public static class ObjectType extends org.jsonx.schema.Object {
    /** Specifies whether the object is abstract. Default: false. **/
    @org.jsonx.BooleanProperty(name="@abstract", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.Boolean get40abstract() {
      return _40abstract;
    }

    /** Specifies whether the object is abstract. Default: false. **/
    public ObjectType set40abstract(final java.lang.Boolean _40abstract) {
      this._40abstract = _40abstract;
      return this;
    }

    private java.lang.Boolean _40abstract;

    public ObjectType set40(final java.lang.String _40) {
      super.set40(_40);
      return (ObjectType)this;
    }

    /** Specifies the name of the type to extend. Optional. **/
    public ObjectType set40extends(final java.lang.String _40extends) {
      super.set40extends(_40extends);
      return (ObjectType)this;
    }

    public ObjectType set40properties(final org.jsonx.schema.Object._40properties _40properties) {
      super.set40properties(_40properties);
      return (ObjectType)this;
    }

    /** Defines text comments. Optional. **/
    public ObjectType set40doc(final java.lang.String _40doc) {
      super.set40doc(_40doc);
      return (ObjectType)this;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.ObjectType) || !super.equals(obj))
        return false;

      final org.jsonx.schema.ObjectType that = (org.jsonx.schema.ObjectType)obj;
      if (!org.libj.lang.ObjectUtil.equals(_40abstract, that._40abstract))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 926406452 * 31 + super.hashCode();
      if (_40abstract != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40abstract);

      return hashCode;
    }
  }

  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/schema-0.4.jsdx")
  public abstract static class Reference extends org.jsonx.schema.Member {
    @org.jsonx.StringProperty(name="@", pattern="reference", nullable=false)
    public java.lang.String get40() {
      return _40;
    }

    public Reference set40(final java.lang.String _40) {
      this._40 = _40;
      return this;
    }

    private java.lang.String _40;

    /** Specifies the name of the referenced type. Required. **/
    @org.jsonx.StringProperty(name="@type", pattern="\\S|\\S.*\\S", nullable=false)
    public java.lang.String get40type() {
      return _40type;
    }

    /** Specifies the name of the referenced type. Required. **/
    public Reference set40type(final java.lang.String _40type) {
      this._40type = _40type;
      return this;
    }

    private java.lang.String _40type;

    /** Defines text comments. Optional. **/
    public Reference set40doc(final java.lang.String _40doc) {
      super.set40doc(_40doc);
      return (Reference)this;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.Reference) || !super.equals(obj))
        return false;

      final org.jsonx.schema.Reference that = (org.jsonx.schema.Reference)obj;
      if (!org.libj.lang.ObjectUtil.equals(_40, that._40))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(_40type, that._40type))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 1841260496 * 31 + super.hashCode();
      if (_40 != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40);

      if (_40type != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40type);

      return hashCode;
    }
  }

  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/schema-0.4.jsdx")
  public static class ReferenceElement extends org.jsonx.schema.Reference {
    @org.jsonx.BooleanProperty(name="@nullable", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.Boolean get40nullable() {
      return _40nullable;
    }

    public ReferenceElement set40nullable(final java.lang.Boolean _40nullable) {
      this._40nullable = _40nullable;
      return this;
    }

    private java.lang.Boolean _40nullable;

    @org.jsonx.StringProperty(name="@minOccurs", pattern="\\d+", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String get40minOccurs() {
      return _40minOccurs;
    }

    public ReferenceElement set40minOccurs(final java.lang.String _40minOccurs) {
      this._40minOccurs = _40minOccurs;
      return this;
    }

    private java.lang.String _40minOccurs;

    @org.jsonx.StringProperty(name="@maxOccurs", pattern="\\d+|unbounded", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String get40maxOccurs() {
      return _40maxOccurs;
    }

    public ReferenceElement set40maxOccurs(final java.lang.String _40maxOccurs) {
      this._40maxOccurs = _40maxOccurs;
      return this;
    }

    private java.lang.String _40maxOccurs;

    public ReferenceElement set40(final java.lang.String _40) {
      super.set40(_40);
      return (ReferenceElement)this;
    }

    /** Specifies the name of the referenced type. Required. **/
    public ReferenceElement set40type(final java.lang.String _40type) {
      super.set40type(_40type);
      return (ReferenceElement)this;
    }

    /** Defines text comments. Optional. **/
    public ReferenceElement set40doc(final java.lang.String _40doc) {
      super.set40doc(_40doc);
      return (ReferenceElement)this;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.ReferenceElement) || !super.equals(obj))
        return false;

      final org.jsonx.schema.ReferenceElement that = (org.jsonx.schema.ReferenceElement)obj;
      if (!org.libj.lang.ObjectUtil.equals(_40nullable, that._40nullable))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(_40minOccurs, that._40minOccurs))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(_40maxOccurs, that._40maxOccurs))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 1837032716 * 31 + super.hashCode();
      if (_40nullable != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40nullable);

      if (_40minOccurs != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40minOccurs);

      if (_40maxOccurs != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40maxOccurs);

      return hashCode;
    }
  }

  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/schema-0.4.jsdx")
  public static class ReferenceProperty extends org.jsonx.schema.Reference {
    @org.jsonx.BooleanProperty(name="@nullable", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.Boolean get40nullable() {
      return _40nullable;
    }

    public ReferenceProperty set40nullable(final java.lang.Boolean _40nullable) {
      this._40nullable = _40nullable;
      return this;
    }

    private java.lang.Boolean _40nullable;

    @org.jsonx.StringProperty(name="@use", pattern="required|optional", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String get40use() {
      return _40use;
    }

    public ReferenceProperty set40use(final java.lang.String _40use) {
      this._40use = _40use;
      return this;
    }

    private java.lang.String _40use;

    public ReferenceProperty set40(final java.lang.String _40) {
      super.set40(_40);
      return (ReferenceProperty)this;
    }

    /** Specifies the name of the referenced type. Required. **/
    public ReferenceProperty set40type(final java.lang.String _40type) {
      super.set40type(_40type);
      return (ReferenceProperty)this;
    }

    /** Defines text comments. Optional. **/
    public ReferenceProperty set40doc(final java.lang.String _40doc) {
      super.set40doc(_40doc);
      return (ReferenceProperty)this;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.ReferenceProperty) || !super.equals(obj))
        return false;

      final org.jsonx.schema.ReferenceProperty that = (org.jsonx.schema.ReferenceProperty)obj;
      if (!org.libj.lang.ObjectUtil.equals(_40nullable, that._40nullable))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(_40use, that._40use))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 128637381 * 31 + super.hashCode();
      if (_40nullable != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40nullable);

      if (_40use != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40use);

      return hashCode;
    }
  }

  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/schema-0.4.jsdx")
  public static class Schema extends org.jsonx.schema.Documented {
    @org.jsonx.StringProperty(name="@ns", pattern="http://www.jsonx.org/schema-0.4.jsd", nullable=false)
    public java.lang.String get40ns() {
      return _40ns;
    }

    public Schema set40ns(final java.lang.String _40ns) {
      this._40ns = _40ns;
      return this;
    }

    private java.lang.String _40ns;

    @org.jsonx.StringProperty(name="@schemaLocation", pattern="((\\S|\\S.*\\S) (\\S|\\S.*\\S))+", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String get40schemaLocation() {
      return _40schemaLocation;
    }

    public Schema set40schemaLocation(final java.lang.String _40schemaLocation) {
      this._40schemaLocation = _40schemaLocation;
      return this;
    }

    private java.lang.String _40schemaLocation;

    @org.jsonx.StringProperty(name="@targetNamespace", pattern="\\S|\\S.*\\S", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String get40targetNamespace() {
      return _40targetNamespace;
    }

    public Schema set40targetNamespace(final java.lang.String _40targetNamespace) {
      this._40targetNamespace = _40targetNamespace;
      return this;
    }

    private java.lang.String _40targetNamespace;

    @org.jsonx.ObjectElement(id=0, type=org.jsonx.schema.Import.class)
    @org.jsonx.ArrayProperty(name="@imports", elementIds={0}, use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.util.List<org.jsonx.schema.Import> get40imports() {
      return _40imports;
    }

    public Schema set40imports(final java.util.List<org.jsonx.schema.Import> _40imports) {
      this._40imports = _40imports;
      return this;
    }

    private java.util.List<org.jsonx.schema.Import> _40imports;

    @org.jsonx.AnyProperty(name="[a-zA-Z_][-a-zA-Z\\d_]*", types={@org.jsonx.t(objects=org.jsonx.schema.Array.class), @org.jsonx.t(objects=org.jsonx.schema.Boolean.class), @org.jsonx.t(objects=org.jsonx.schema.Number.class), @org.jsonx.t(objects=org.jsonx.schema.ObjectType.class), @org.jsonx.t(objects=org.jsonx.schema.String.class)}, nullable=false)
    public java.util.LinkedHashMap<java.lang.String,org.jsonx.schema.Member> get5ba2dZA2dZ__5d5b2dA2dZA2dZ5cD__5d2a() {
      return _5ba2dZA2dZ__5d5b2dA2dZA2dZ5cD__5d2a;
    }

    public Schema set5ba2dZA2dZ__5d5b2dA2dZA2dZ5cD__5d2a(final java.util.LinkedHashMap<java.lang.String,org.jsonx.schema.Member> _5ba2dZA2dZ__5d5b2dA2dZA2dZ5cD__5d2a) {
      this._5ba2dZA2dZ__5d5b2dA2dZA2dZ5cD__5d2a = _5ba2dZA2dZ__5d5b2dA2dZA2dZ5cD__5d2a;
      return this;
    }

    private java.util.LinkedHashMap<java.lang.String,org.jsonx.schema.Member> _5ba2dZA2dZ__5d5b2dA2dZA2dZ5cD__5d2a;

    /** Defines text comments. Optional. **/
    public Schema set40doc(final java.lang.String _40doc) {
      super.set40doc(_40doc);
      return (Schema)this;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.Schema) || !super.equals(obj))
        return false;

      final org.jsonx.schema.Schema that = (org.jsonx.schema.Schema)obj;
      if (!org.libj.lang.ObjectUtil.equals(_40ns, that._40ns))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(_40schemaLocation, that._40schemaLocation))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(_40targetNamespace, that._40targetNamespace))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(_40imports, that._40imports))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(_5ba2dZA2dZ__5d5b2dA2dZA2dZ5cD__5d2a, that._5ba2dZA2dZ__5d5b2dA2dZA2dZ5cD__5d2a))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 1381524284 * 31 + super.hashCode();
      if (_40ns != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40ns);

      if (_40schemaLocation != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40schemaLocation);

      if (_40targetNamespace != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40targetNamespace);

      if (_40imports != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40imports);

      if (_5ba2dZA2dZ__5d5b2dA2dZA2dZ5cD__5d2a != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_5ba2dZA2dZ__5d5b2dA2dZA2dZ5cD__5d2a);

      return hashCode;
    }
  }

  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/schema-0.4.jsdx")
  public static class String extends org.jsonx.schema.Member {
    @org.jsonx.StringProperty(name="@", pattern="string", nullable=false)
    public java.lang.String get40() {
      return _40;
    }

    public String set40(final java.lang.String _40) {
      this._40 = _40;
      return this;
    }

    private java.lang.String _40;

    @org.jsonx.StringProperty(name="@pattern", pattern="\\S|\\S.*\\S", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String get40pattern() {
      return _40pattern;
    }

    public String set40pattern(final java.lang.String _40pattern) {
      this._40pattern = _40pattern;
      return this;
    }

    private java.lang.String _40pattern;

    /** Defines text comments. Optional. **/
    public String set40doc(final java.lang.String _40doc) {
      super.set40doc(_40doc);
      return (String)this;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.String) || !super.equals(obj))
        return false;

      final org.jsonx.schema.String that = (org.jsonx.schema.String)obj;
      if (!org.libj.lang.ObjectUtil.equals(_40, that._40))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(_40pattern, that._40pattern))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 1397525932 * 31 + super.hashCode();
      if (_40 != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40);

      if (_40pattern != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40pattern);

      return hashCode;
    }
  }

  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/schema-0.4.jsdx")
  public static class StringElement extends org.jsonx.schema.String {
    @org.jsonx.BooleanProperty(name="@nullable", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.Boolean get40nullable() {
      return _40nullable;
    }

    public StringElement set40nullable(final java.lang.Boolean _40nullable) {
      this._40nullable = _40nullable;
      return this;
    }

    private java.lang.Boolean _40nullable;

    @org.jsonx.StringProperty(name="@minOccurs", pattern="\\d+", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String get40minOccurs() {
      return _40minOccurs;
    }

    public StringElement set40minOccurs(final java.lang.String _40minOccurs) {
      this._40minOccurs = _40minOccurs;
      return this;
    }

    private java.lang.String _40minOccurs;

    @org.jsonx.StringProperty(name="@maxOccurs", pattern="\\d+|unbounded", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String get40maxOccurs() {
      return _40maxOccurs;
    }

    public StringElement set40maxOccurs(final java.lang.String _40maxOccurs) {
      this._40maxOccurs = _40maxOccurs;
      return this;
    }

    private java.lang.String _40maxOccurs;

    public StringElement set40(final java.lang.String _40) {
      super.set40(_40);
      return (StringElement)this;
    }

    public StringElement set40pattern(final java.lang.String _40pattern) {
      super.set40pattern(_40pattern);
      return (StringElement)this;
    }

    /** Defines text comments. Optional. **/
    public StringElement set40doc(final java.lang.String _40doc) {
      super.set40doc(_40doc);
      return (StringElement)this;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.StringElement) || !super.equals(obj))
        return false;

      final org.jsonx.schema.StringElement that = (org.jsonx.schema.StringElement)obj;
      if (!org.libj.lang.ObjectUtil.equals(_40nullable, that._40nullable))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(_40minOccurs, that._40minOccurs))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(_40maxOccurs, that._40maxOccurs))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 1884336048 * 31 + super.hashCode();
      if (_40nullable != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40nullable);

      if (_40minOccurs != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40minOccurs);

      if (_40maxOccurs != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40maxOccurs);

      return hashCode;
    }
  }

  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/schema-0.4.jsdx")
  public static class StringProperty extends org.jsonx.schema.String {
    @org.jsonx.BooleanProperty(name="@nullable", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.Boolean get40nullable() {
      return _40nullable;
    }

    public StringProperty set40nullable(final java.lang.Boolean _40nullable) {
      this._40nullable = _40nullable;
      return this;
    }

    private java.lang.Boolean _40nullable;

    @org.jsonx.StringProperty(name="@use", pattern="required|optional", use=org.jsonx.Use.OPTIONAL, nullable=false)
    public java.lang.String get40use() {
      return _40use;
    }

    public StringProperty set40use(final java.lang.String _40use) {
      this._40use = _40use;
      return this;
    }

    private java.lang.String _40use;

    public StringProperty set40(final java.lang.String _40) {
      super.set40(_40);
      return (StringProperty)this;
    }

    public StringProperty set40pattern(final java.lang.String _40pattern) {
      super.set40pattern(_40pattern);
      return (StringProperty)this;
    }

    /** Defines text comments. Optional. **/
    public StringProperty set40doc(final java.lang.String _40doc) {
      super.set40doc(_40doc);
      return (StringProperty)this;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.StringProperty) || !super.equals(obj))
        return false;

      final org.jsonx.schema.StringProperty that = (org.jsonx.schema.StringProperty)obj;
      if (!org.libj.lang.ObjectUtil.equals(_40nullable, that._40nullable))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(_40use, that._40use))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 1595040673 * 31 + super.hashCode();
      if (_40nullable != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40nullable);

      if (_40use != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40use);

      return hashCode;
    }
  }
}