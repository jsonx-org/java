package org.jsonx;

@java.lang.SuppressWarnings("all")
public class schema {
  public static class Any extends org.jsonx.schema.Member {
    @org.jsonx.StringProperty(name="jx:type", pattern="any", nullable=false)
    private java.lang.String jx_3aType;

    public void setJx_3aType(final java.lang.String jx_3aType) {
      this.jx_3aType = jx_3aType;
    }

    public java.lang.String getJx_3aType() {
      return jx_3aType;
    }

    @org.jsonx.StringProperty(pattern="\\S+( \\S+)*", use=org.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String types;

    public void setTypes(final java.lang.String types) {
      this.types = types;
    }

    public java.lang.String getTypes() {
      return types;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.Any) || !super.equals(obj))
        return false;

      final org.jsonx.schema.Any that = (org.jsonx.schema.Any)obj;
      if (that.jx_3aType != null ? !that.jx_3aType.equals(jx_3aType) : jx_3aType != null)
        return false;

      if (that.types != null ? !that.types.equals(types) : types != null)
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 188459569 * 31 + super.hashCode();
      hashCode = 31 * hashCode + java.util.Objects.hashCode(jx_3aType);
      hashCode = 31 * hashCode + java.util.Objects.hashCode(types);
      return hashCode;
    }
  }

  public static class AnyElement extends org.jsonx.schema.Any {
    @org.jsonx.BooleanProperty(use=org.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.Boolean nullable;

    public void setNullable(final java.lang.Boolean nullable) {
      this.nullable = nullable;
    }

    public java.lang.Boolean getNullable() {
      return nullable;
    }

    @org.jsonx.StringProperty(pattern="[\\d]+", use=org.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String minOccurs;

    public void setMinOccurs(final java.lang.String minOccurs) {
      this.minOccurs = minOccurs;
    }

    public java.lang.String getMinOccurs() {
      return minOccurs;
    }

    @org.jsonx.StringProperty(pattern="([\\d]+|unbounded)", use=org.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String maxOccurs;

    public void setMaxOccurs(final java.lang.String maxOccurs) {
      this.maxOccurs = maxOccurs;
    }

    public java.lang.String getMaxOccurs() {
      return maxOccurs;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.AnyElement) || !super.equals(obj))
        return false;

      final org.jsonx.schema.AnyElement that = (org.jsonx.schema.AnyElement)obj;
      if (that.nullable != null ? !that.nullable.equals(nullable) : nullable != null)
        return false;

      if (that.minOccurs != null ? !that.minOccurs.equals(minOccurs) : minOccurs != null)
        return false;

      if (that.maxOccurs != null ? !that.maxOccurs.equals(maxOccurs) : maxOccurs != null)
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 1449550731 * 31 + super.hashCode();
      hashCode = 31 * hashCode + java.util.Objects.hashCode(nullable);
      hashCode = 31 * hashCode + java.util.Objects.hashCode(minOccurs);
      hashCode = 31 * hashCode + java.util.Objects.hashCode(maxOccurs);
      return hashCode;
    }
  }

  public static class AnyProperty extends org.jsonx.schema.Any {
    @org.jsonx.BooleanProperty(use=org.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.Boolean nullable;

    public void setNullable(final java.lang.Boolean nullable) {
      this.nullable = nullable;
    }

    public java.lang.Boolean getNullable() {
      return nullable;
    }

    @org.jsonx.StringProperty(pattern="(required|optional)", use=org.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String use;

    public void setUse(final java.lang.String use) {
      this.use = use;
    }

    public java.lang.String getUse() {
      return use;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.AnyProperty) || !super.equals(obj))
        return false;

      final org.jsonx.schema.AnyProperty that = (org.jsonx.schema.AnyProperty)obj;
      if (that.nullable != null ? !that.nullable.equals(nullable) : nullable != null)
        return false;

      if (that.use != null ? !that.use.equals(use) : use != null)
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 1001597734 * 31 + super.hashCode();
      hashCode = 31 * hashCode + java.util.Objects.hashCode(nullable);
      hashCode = 31 * hashCode + java.util.Objects.hashCode(use);
      return hashCode;
    }
  }

  public static class Array extends org.jsonx.schema.Member {
    @org.jsonx.StringProperty(name="jx:type", pattern="array", nullable=false)
    private java.lang.String jx_3aType;

    public void setJx_3aType(final java.lang.String jx_3aType) {
      this.jx_3aType = jx_3aType;
    }

    public java.lang.String getJx_3aType() {
      return jx_3aType;
    }

    @org.jsonx.StringProperty(pattern="[\\d]+", use=org.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String minIterate;

    /** Specifies the minimum inclusive number of iterations of child elements. Default: 1. **/
    public void setMinIterate(final java.lang.String minIterate) {
      this.minIterate = minIterate;
    }

    /** Specifies the minimum inclusive number of iterations of child elements. Default: 1. **/
    public java.lang.String getMinIterate() {
      return minIterate;
    }

    @org.jsonx.StringProperty(pattern="([\\d]+|unbounded)", use=org.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String maxIterate;

    /** Specifies the maximum inclusive number of iterations of child elements. Default: 1. **/
    public void setMaxIterate(final java.lang.String maxIterate) {
      this.maxIterate = maxIterate;
    }

    /** Specifies the maximum inclusive number of iterations of child elements. Default: 1. **/
    public java.lang.String getMaxIterate() {
      return maxIterate;
    }

    @org.jsonx.ObjectElement(id=6, type=org.jsonx.schema.ObjectElement.class, minOccurs=0, nullable=false)
    @org.jsonx.ObjectElement(id=5, type=org.jsonx.schema.StringElement.class, minOccurs=0, nullable=false)
    @org.jsonx.ObjectElement(id=4, type=org.jsonx.schema.ReferenceElement.class, minOccurs=0, nullable=false)
    @org.jsonx.ObjectElement(id=3, type=org.jsonx.schema.NumberElement.class, minOccurs=0, nullable=false)
    @org.jsonx.ObjectElement(id=2, type=org.jsonx.schema.BooleanElement.class, minOccurs=0, nullable=false)
    @org.jsonx.ObjectElement(id=1, type=org.jsonx.schema.ArrayElement.class, minOccurs=0, nullable=false)
    @org.jsonx.ObjectElement(id=0, type=org.jsonx.schema.AnyElement.class, minOccurs=0, nullable=false)
    @org.jsonx.ArrayProperty(elementIds={0, 1, 2, 3, 4, 5, 6}, maxIterate=2147483647, nullable=false)
    private java.util.List<java.lang.Object> elements;

    public void setElements(final java.util.List<java.lang.Object> elements) {
      this.elements = elements;
    }

    public java.util.List<java.lang.Object> getElements() {
      return elements;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.Array) || !super.equals(obj))
        return false;

      final org.jsonx.schema.Array that = (org.jsonx.schema.Array)obj;
      if (that.jx_3aType != null ? !that.jx_3aType.equals(jx_3aType) : jx_3aType != null)
        return false;

      if (that.minIterate != null ? !that.minIterate.equals(minIterate) : minIterate != null)
        return false;

      if (that.maxIterate != null ? !that.maxIterate.equals(maxIterate) : maxIterate != null)
        return false;

      if (that.elements != null ? !that.elements.equals(elements) : elements != null)
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 721134942 * 31 + super.hashCode();
      hashCode = 31 * hashCode + java.util.Objects.hashCode(jx_3aType);
      hashCode = 31 * hashCode + java.util.Objects.hashCode(minIterate);
      hashCode = 31 * hashCode + java.util.Objects.hashCode(maxIterate);
      hashCode = 31 * hashCode + java.util.Objects.hashCode(elements);
      return hashCode;
    }
  }

  public static class ArrayElement extends org.jsonx.schema.Array {
    @org.jsonx.BooleanProperty(use=org.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.Boolean nullable;

    public void setNullable(final java.lang.Boolean nullable) {
      this.nullable = nullable;
    }

    public java.lang.Boolean getNullable() {
      return nullable;
    }

    @org.jsonx.StringProperty(pattern="[\\d]+", use=org.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String minOccurs;

    public void setMinOccurs(final java.lang.String minOccurs) {
      this.minOccurs = minOccurs;
    }

    public java.lang.String getMinOccurs() {
      return minOccurs;
    }

    @org.jsonx.StringProperty(pattern="([\\d]+|unbounded)", use=org.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String maxOccurs;

    public void setMaxOccurs(final java.lang.String maxOccurs) {
      this.maxOccurs = maxOccurs;
    }

    public java.lang.String getMaxOccurs() {
      return maxOccurs;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.ArrayElement) || !super.equals(obj))
        return false;

      final org.jsonx.schema.ArrayElement that = (org.jsonx.schema.ArrayElement)obj;
      if (that.nullable != null ? !that.nullable.equals(nullable) : nullable != null)
        return false;

      if (that.minOccurs != null ? !that.minOccurs.equals(minOccurs) : minOccurs != null)
        return false;

      if (that.maxOccurs != null ? !that.maxOccurs.equals(maxOccurs) : maxOccurs != null)
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = -614798402 * 31 + super.hashCode();
      hashCode = 31 * hashCode + java.util.Objects.hashCode(nullable);
      hashCode = 31 * hashCode + java.util.Objects.hashCode(minOccurs);
      hashCode = 31 * hashCode + java.util.Objects.hashCode(maxOccurs);
      return hashCode;
    }
  }

  public static class ArrayProperty extends org.jsonx.schema.Array {
    @org.jsonx.BooleanProperty(use=org.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.Boolean nullable;

    public void setNullable(final java.lang.Boolean nullable) {
      this.nullable = nullable;
    }

    public java.lang.Boolean getNullable() {
      return nullable;
    }

    @org.jsonx.StringProperty(pattern="(required|optional)", use=org.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String use;

    public void setUse(final java.lang.String use) {
      this.use = use;
    }

    public java.lang.String getUse() {
      return use;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.ArrayProperty) || !super.equals(obj))
        return false;

      final org.jsonx.schema.ArrayProperty that = (org.jsonx.schema.ArrayProperty)obj;
      if (that.nullable != null ? !that.nullable.equals(nullable) : nullable != null)
        return false;

      if (that.use != null ? !that.use.equals(use) : use != null)
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 1431284051 * 31 + super.hashCode();
      hashCode = 31 * hashCode + java.util.Objects.hashCode(nullable);
      hashCode = 31 * hashCode + java.util.Objects.hashCode(use);
      return hashCode;
    }
  }

  public static class Boolean extends org.jsonx.schema.Member {
    @org.jsonx.StringProperty(name="jx:type", pattern="boolean", nullable=false)
    private java.lang.String jx_3aType;

    public void setJx_3aType(final java.lang.String jx_3aType) {
      this.jx_3aType = jx_3aType;
    }

    public java.lang.String getJx_3aType() {
      return jx_3aType;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.Boolean) || !super.equals(obj))
        return false;

      final org.jsonx.schema.Boolean that = (org.jsonx.schema.Boolean)obj;
      if (that.jx_3aType != null ? !that.jx_3aType.equals(jx_3aType) : jx_3aType != null)
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = -1974865427 * 31 + super.hashCode();
      hashCode = 31 * hashCode + java.util.Objects.hashCode(jx_3aType);
      return hashCode;
    }
  }

  public static class BooleanElement extends org.jsonx.schema.Boolean {
    @org.jsonx.BooleanProperty(use=org.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.Boolean nullable;

    public void setNullable(final java.lang.Boolean nullable) {
      this.nullable = nullable;
    }

    public java.lang.Boolean getNullable() {
      return nullable;
    }

    @org.jsonx.StringProperty(pattern="[\\d]+", use=org.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String minOccurs;

    public void setMinOccurs(final java.lang.String minOccurs) {
      this.minOccurs = minOccurs;
    }

    public java.lang.String getMinOccurs() {
      return minOccurs;
    }

    @org.jsonx.StringProperty(pattern="([\\d]+|unbounded)", use=org.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String maxOccurs;

    public void setMaxOccurs(final java.lang.String maxOccurs) {
      this.maxOccurs = maxOccurs;
    }

    public java.lang.String getMaxOccurs() {
      return maxOccurs;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.BooleanElement) || !super.equals(obj))
        return false;

      final org.jsonx.schema.BooleanElement that = (org.jsonx.schema.BooleanElement)obj;
      if (that.nullable != null ? !that.nullable.equals(nullable) : nullable != null)
        return false;

      if (that.minOccurs != null ? !that.minOccurs.equals(minOccurs) : minOccurs != null)
        return false;

      if (that.maxOccurs != null ? !that.maxOccurs.equals(maxOccurs) : maxOccurs != null)
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 677851215 * 31 + super.hashCode();
      hashCode = 31 * hashCode + java.util.Objects.hashCode(nullable);
      hashCode = 31 * hashCode + java.util.Objects.hashCode(minOccurs);
      hashCode = 31 * hashCode + java.util.Objects.hashCode(maxOccurs);
      return hashCode;
    }
  }

  public static class BooleanProperty extends org.jsonx.schema.Boolean {
    @org.jsonx.BooleanProperty(use=org.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.Boolean nullable;

    public void setNullable(final java.lang.Boolean nullable) {
      this.nullable = nullable;
    }

    public java.lang.Boolean getNullable() {
      return nullable;
    }

    @org.jsonx.StringProperty(pattern="(required|optional)", use=org.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String use;

    public void setUse(final java.lang.String use) {
      this.use = use;
    }

    public java.lang.String getUse() {
      return use;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.BooleanProperty) || !super.equals(obj))
        return false;

      final org.jsonx.schema.BooleanProperty that = (org.jsonx.schema.BooleanProperty)obj;
      if (that.nullable != null ? !that.nullable.equals(nullable) : nullable != null)
        return false;

      if (that.use != null ? !that.use.equals(use) : use != null)
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = -1446250782 * 31 + super.hashCode();
      hashCode = 31 * hashCode + java.util.Objects.hashCode(nullable);
      hashCode = 31 * hashCode + java.util.Objects.hashCode(use);
      return hashCode;
    }
  }

  public static abstract class Documented implements org.jsonx.JxObject {
    @org.jsonx.StringProperty
    private java.lang.String doc;

    /** Defines text comments. Optional. **/
    public void setDoc(final java.lang.String doc) {
      this.doc = doc;
    }

    /** Defines text comments. Optional. **/
    public java.lang.String getDoc() {
      return doc;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.Documented))
        return false;

      final org.jsonx.schema.Documented that = (org.jsonx.schema.Documented)obj;
      if (that.doc != null ? !that.doc.equals(doc) : doc != null)
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = -955389163;
      hashCode = 31 * hashCode + java.util.Objects.hashCode(doc);
      return hashCode;
    }

    @java.lang.Override
    public java.lang.String toString() {
      return org.jsonx.JxEncoder.get().marshal(this);
    }
  }

  public static abstract class Member extends org.jsonx.schema.Documented {
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
    private java.lang.String jx_3aType;

    public void setJx_3aType(final java.lang.String jx_3aType) {
      this.jx_3aType = jx_3aType;
    }

    public java.lang.String getJx_3aType() {
      return jx_3aType;
    }

    @org.jsonx.StringProperty(pattern="[\\(\\[](-?(0|[1-9]\\d*)(\\.\\d+)?([eE][+-]?([1-9]\\d*))?)?,(-?(0|[1-9]\\d*)(\\.\\d+)?([eE][+-]?([1-9]\\d*))?)?[\\)\\]]", use=org.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String range;

    /**        Specifies the value range in interval notation:       Open (exclusive) interval: (min,max)       Closed (inclusive) interval: [min,max]       Half-open or half-closed interval: [min,max)       Degenerate interval: [val] **/
    public void setRange(final java.lang.String range) {
      this.range = range;
    }

    /**        Specifies the value range in interval notation:       Open (exclusive) interval: (min,max)       Closed (inclusive) interval: [min,max]       Half-open or half-closed interval: [min,max)       Degenerate interval: [val] **/
    public java.lang.String getRange() {
      return range;
    }

    @org.jsonx.NumberProperty(scale=0, use=org.jsonx.Use.OPTIONAL, nullable=false)
    private java.math.BigInteger scale;

    /** The number of digits to the right of the decimal point. If a value is not specified, the scale is unbounded. **/
    public void setScale(final java.math.BigInteger scale) {
      this.scale = scale;
    }

    /** The number of digits to the right of the decimal point. If a value is not specified, the scale is unbounded. **/
    public java.math.BigInteger getScale() {
      return scale;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.Number) || !super.equals(obj))
        return false;

      final org.jsonx.schema.Number that = (org.jsonx.schema.Number)obj;
      if (that.jx_3aType != null ? !that.jx_3aType.equals(jx_3aType) : jx_3aType != null)
        return false;

      if (that.range != null ? !that.range.equals(range) : range != null)
        return false;

      if (that.scale != null ? !that.scale.equals(scale) : scale != null)
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 1255147748 * 31 + super.hashCode();
      hashCode = 31 * hashCode + java.util.Objects.hashCode(jx_3aType);
      hashCode = 31 * hashCode + java.util.Objects.hashCode(range);
      hashCode = 31 * hashCode + java.util.Objects.hashCode(scale);
      return hashCode;
    }
  }

  public static class NumberElement extends org.jsonx.schema.Number {
    @org.jsonx.BooleanProperty(use=org.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.Boolean nullable;

    public void setNullable(final java.lang.Boolean nullable) {
      this.nullable = nullable;
    }

    public java.lang.Boolean getNullable() {
      return nullable;
    }

    @org.jsonx.StringProperty(pattern="[\\d]+", use=org.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String minOccurs;

    public void setMinOccurs(final java.lang.String minOccurs) {
      this.minOccurs = minOccurs;
    }

    public java.lang.String getMinOccurs() {
      return minOccurs;
    }

    @org.jsonx.StringProperty(pattern="([\\d]+|unbounded)", use=org.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String maxOccurs;

    public void setMaxOccurs(final java.lang.String maxOccurs) {
      this.maxOccurs = maxOccurs;
    }

    public java.lang.String getMaxOccurs() {
      return maxOccurs;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.NumberElement) || !super.equals(obj))
        return false;

      final org.jsonx.schema.NumberElement that = (org.jsonx.schema.NumberElement)obj;
      if (that.nullable != null ? !that.nullable.equals(nullable) : nullable != null)
        return false;

      if (that.minOccurs != null ? !that.minOccurs.equals(minOccurs) : minOccurs != null)
        return false;

      if (that.maxOccurs != null ? !that.maxOccurs.equals(maxOccurs) : maxOccurs != null)
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 2034148728 * 31 + super.hashCode();
      hashCode = 31 * hashCode + java.util.Objects.hashCode(nullable);
      hashCode = 31 * hashCode + java.util.Objects.hashCode(minOccurs);
      hashCode = 31 * hashCode + java.util.Objects.hashCode(maxOccurs);
      return hashCode;
    }
  }

  public static class NumberProperty extends org.jsonx.schema.Number {
    @org.jsonx.BooleanProperty(use=org.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.Boolean nullable;

    public void setNullable(final java.lang.Boolean nullable) {
      this.nullable = nullable;
    }

    public java.lang.Boolean getNullable() {
      return nullable;
    }

    @org.jsonx.StringProperty(pattern="(required|optional)", use=org.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String use;

    public void setUse(final java.lang.String use) {
      this.use = use;
    }

    public java.lang.String getUse() {
      return use;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.NumberProperty) || !super.equals(obj))
        return false;

      final org.jsonx.schema.NumberProperty that = (org.jsonx.schema.NumberProperty)obj;
      if (that.nullable != null ? !that.nullable.equals(nullable) : nullable != null)
        return false;

      if (that.use != null ? !that.use.equals(use) : use != null)
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 1944266457 * 31 + super.hashCode();
      hashCode = 31 * hashCode + java.util.Objects.hashCode(nullable);
      hashCode = 31 * hashCode + java.util.Objects.hashCode(use);
      return hashCode;
    }
  }

  public static abstract class Object extends org.jsonx.schema.Member {
    public static class Properties implements org.jsonx.JxObject {
      @org.jsonx.AnyProperty(name=".*", types={@org.jsonx.t(objects=org.jsonx.schema.AnyProperty.class), @org.jsonx.t(objects=org.jsonx.schema.ArrayProperty.class), @org.jsonx.t(objects=org.jsonx.schema.BooleanProperty.class), @org.jsonx.t(objects=org.jsonx.schema.NumberProperty.class), @org.jsonx.t(objects=org.jsonx.schema.ObjectProperty.class), @org.jsonx.t(objects=org.jsonx.schema.ReferenceProperty.class), @org.jsonx.t(objects=org.jsonx.schema.StringProperty.class)}, nullable=false)
      public final java.util.LinkedHashMap<java.lang.String,java.lang.Object> _2e_2a = new java.util.LinkedHashMap<>();

      @java.lang.Override
      public boolean equals(final java.lang.Object obj) {
        if (obj == this)
          return true;

        if (!(obj instanceof org.jsonx.schema.Object.Properties))
          return false;

        final org.jsonx.schema.Object.Properties that = (org.jsonx.schema.Object.Properties)obj;
        if (that._2e_2a != null ? !that._2e_2a.equals(_2e_2a) : _2e_2a != null)
          return false;

        return true;
      }

      @java.lang.Override
      public int hashCode() {
        int hashCode = -946499747;
        hashCode = 31 * hashCode + java.util.Objects.hashCode(_2e_2a);
        return hashCode;
      }

      @java.lang.Override
      public java.lang.String toString() {
        return org.jsonx.JxEncoder.get().marshal(this);
      }
    }
    @org.jsonx.StringProperty(name="jx:type", pattern="object", nullable=false)
    private java.lang.String jx_3aType;

    public void setJx_3aType(final java.lang.String jx_3aType) {
      this.jx_3aType = jx_3aType;
    }

    public java.lang.String getJx_3aType() {
      return jx_3aType;
    }

    @org.jsonx.StringProperty(name="extends", use=org.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String _extends;

    /** Specifies the name of the type to extend. Optional. **/
    public void setExtends(final java.lang.String _extends) {
      this._extends = _extends;
    }

    /** Specifies the name of the type to extend. Optional. **/
    public java.lang.String getExtends() {
      return _extends;
    }

    @org.jsonx.ObjectProperty(use=org.jsonx.Use.OPTIONAL, nullable=false)
    private org.jsonx.schema.Object.Properties properties;

    public void setProperties(final org.jsonx.schema.Object.Properties properties) {
      this.properties = properties;
    }

    public org.jsonx.schema.Object.Properties getProperties() {
      return properties;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.Object) || !super.equals(obj))
        return false;

      final org.jsonx.schema.Object that = (org.jsonx.schema.Object)obj;
      if (that.jx_3aType != null ? !that.jx_3aType.equals(jx_3aType) : jx_3aType != null)
        return false;

      if (that._extends != null ? !that._extends.equals(_extends) : _extends != null)
        return false;

      if (that.properties != null ? !that.properties.equals(properties) : properties != null)
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 1266143450 * 31 + super.hashCode();
      hashCode = 31 * hashCode + java.util.Objects.hashCode(jx_3aType);
      hashCode = 31 * hashCode + java.util.Objects.hashCode(_extends);
      hashCode = 31 * hashCode + java.util.Objects.hashCode(properties);
      return hashCode;
    }
  }

  public static class ObjectElement extends org.jsonx.schema.Object {
    @org.jsonx.BooleanProperty(use=org.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.Boolean nullable;

    public void setNullable(final java.lang.Boolean nullable) {
      this.nullable = nullable;
    }

    public java.lang.Boolean getNullable() {
      return nullable;
    }

    @org.jsonx.StringProperty(pattern="[\\d]+", use=org.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String minOccurs;

    public void setMinOccurs(final java.lang.String minOccurs) {
      this.minOccurs = minOccurs;
    }

    public java.lang.String getMinOccurs() {
      return minOccurs;
    }

    @org.jsonx.StringProperty(pattern="([\\d]+|unbounded)", use=org.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String maxOccurs;

    public void setMaxOccurs(final java.lang.String maxOccurs) {
      this.maxOccurs = maxOccurs;
    }

    public java.lang.String getMaxOccurs() {
      return maxOccurs;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.ObjectElement) || !super.equals(obj))
        return false;

      final org.jsonx.schema.ObjectElement that = (org.jsonx.schema.ObjectElement)obj;
      if (that.nullable != null ? !that.nullable.equals(nullable) : nullable != null)
        return false;

      if (that.minOccurs != null ? !that.minOccurs.equals(minOccurs) : minOccurs != null)
        return false;

      if (that.maxOccurs != null ? !that.maxOccurs.equals(maxOccurs) : maxOccurs != null)
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = -1694884670 * 31 + super.hashCode();
      hashCode = 31 * hashCode + java.util.Objects.hashCode(nullable);
      hashCode = 31 * hashCode + java.util.Objects.hashCode(minOccurs);
      hashCode = 31 * hashCode + java.util.Objects.hashCode(maxOccurs);
      return hashCode;
    }
  }

  public static class ObjectProperty extends org.jsonx.schema.Object {
    @org.jsonx.BooleanProperty(use=org.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.Boolean nullable;

    public void setNullable(final java.lang.Boolean nullable) {
      this.nullable = nullable;
    }

    public java.lang.Boolean getNullable() {
      return nullable;
    }

    @org.jsonx.StringProperty(pattern="(required|optional)", use=org.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String use;

    public void setUse(final java.lang.String use) {
      this.use = use;
    }

    public java.lang.String getUse() {
      return use;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.ObjectProperty) || !super.equals(obj))
        return false;

      final org.jsonx.schema.ObjectProperty that = (org.jsonx.schema.ObjectProperty)obj;
      if (that.nullable != null ? !that.nullable.equals(nullable) : nullable != null)
        return false;

      if (that.use != null ? !that.use.equals(use) : use != null)
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = -1986619185 * 31 + super.hashCode();
      hashCode = 31 * hashCode + java.util.Objects.hashCode(nullable);
      hashCode = 31 * hashCode + java.util.Objects.hashCode(use);
      return hashCode;
    }
  }

  public static class ObjectType extends org.jsonx.schema.Object {
    @org.jsonx.BooleanProperty(name="abstract", use=org.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.Boolean _abstract;

    /** Specifies whether the object is abstract. Default: false. **/
    public void setAbstract(final java.lang.Boolean _abstract) {
      this._abstract = _abstract;
    }

    /** Specifies whether the object is abstract. Default: false. **/
    public java.lang.Boolean getAbstract() {
      return _abstract;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.ObjectType) || !super.equals(obj))
        return false;

      final org.jsonx.schema.ObjectType that = (org.jsonx.schema.ObjectType)obj;
      if (that._abstract != null ? !that._abstract.equals(_abstract) : _abstract != null)
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 926406452 * 31 + super.hashCode();
      hashCode = 31 * hashCode + java.util.Objects.hashCode(_abstract);
      return hashCode;
    }
  }

  public static abstract class Reference extends org.jsonx.schema.Member {
    @org.jsonx.StringProperty(name="jx:type", pattern="reference", nullable=false)
    private java.lang.String jx_3aType;

    public void setJx_3aType(final java.lang.String jx_3aType) {
      this.jx_3aType = jx_3aType;
    }

    public java.lang.String getJx_3aType() {
      return jx_3aType;
    }

    @org.jsonx.StringProperty(nullable=false)
    private java.lang.String type;

    /** Specifies the name of the referenced type. Required. **/
    public void setType(final java.lang.String type) {
      this.type = type;
    }

    /** Specifies the name of the referenced type. Required. **/
    public java.lang.String getType() {
      return type;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.Reference) || !super.equals(obj))
        return false;

      final org.jsonx.schema.Reference that = (org.jsonx.schema.Reference)obj;
      if (that.jx_3aType != null ? !that.jx_3aType.equals(jx_3aType) : jx_3aType != null)
        return false;

      if (that.type != null ? !that.type.equals(type) : type != null)
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 1841260496 * 31 + super.hashCode();
      hashCode = 31 * hashCode + java.util.Objects.hashCode(jx_3aType);
      hashCode = 31 * hashCode + java.util.Objects.hashCode(type);
      return hashCode;
    }
  }

  public static class ReferenceElement extends org.jsonx.schema.Reference {
    @org.jsonx.BooleanProperty(use=org.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.Boolean nullable;

    public void setNullable(final java.lang.Boolean nullable) {
      this.nullable = nullable;
    }

    public java.lang.Boolean getNullable() {
      return nullable;
    }

    @org.jsonx.StringProperty(pattern="[\\d]+", use=org.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String minOccurs;

    public void setMinOccurs(final java.lang.String minOccurs) {
      this.minOccurs = minOccurs;
    }

    public java.lang.String getMinOccurs() {
      return minOccurs;
    }

    @org.jsonx.StringProperty(pattern="([\\d]+|unbounded)", use=org.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String maxOccurs;

    public void setMaxOccurs(final java.lang.String maxOccurs) {
      this.maxOccurs = maxOccurs;
    }

    public java.lang.String getMaxOccurs() {
      return maxOccurs;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.ReferenceElement) || !super.equals(obj))
        return false;

      final org.jsonx.schema.ReferenceElement that = (org.jsonx.schema.ReferenceElement)obj;
      if (that.nullable != null ? !that.nullable.equals(nullable) : nullable != null)
        return false;

      if (that.minOccurs != null ? !that.minOccurs.equals(minOccurs) : minOccurs != null)
        return false;

      if (that.maxOccurs != null ? !that.maxOccurs.equals(maxOccurs) : maxOccurs != null)
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 1837032716 * 31 + super.hashCode();
      hashCode = 31 * hashCode + java.util.Objects.hashCode(nullable);
      hashCode = 31 * hashCode + java.util.Objects.hashCode(minOccurs);
      hashCode = 31 * hashCode + java.util.Objects.hashCode(maxOccurs);
      return hashCode;
    }
  }

  public static class ReferenceProperty extends org.jsonx.schema.Reference {
    @org.jsonx.BooleanProperty(use=org.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.Boolean nullable;

    public void setNullable(final java.lang.Boolean nullable) {
      this.nullable = nullable;
    }

    public java.lang.Boolean getNullable() {
      return nullable;
    }

    @org.jsonx.StringProperty(pattern="(required|optional)", use=org.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String use;

    public void setUse(final java.lang.String use) {
      this.use = use;
    }

    public java.lang.String getUse() {
      return use;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.ReferenceProperty) || !super.equals(obj))
        return false;

      final org.jsonx.schema.ReferenceProperty that = (org.jsonx.schema.ReferenceProperty)obj;
      if (that.nullable != null ? !that.nullable.equals(nullable) : nullable != null)
        return false;

      if (that.use != null ? !that.use.equals(use) : use != null)
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 128637381 * 31 + super.hashCode();
      hashCode = 31 * hashCode + java.util.Objects.hashCode(nullable);
      hashCode = 31 * hashCode + java.util.Objects.hashCode(use);
      return hashCode;
    }
  }

  public static class Schema extends org.jsonx.schema.Documented {
    @org.jsonx.StringProperty(name="jx:ns", pattern="http://www.jsonx.org/schema-0.3.jsd", nullable=false)
    private java.lang.String jx_3aNs;

    public void setJx_3aNs(final java.lang.String jx_3aNs) {
      this.jx_3aNs = jx_3aNs;
    }

    public java.lang.String getJx_3aNs() {
      return jx_3aNs;
    }

    @org.jsonx.StringProperty(name="jx:schemaLocation", pattern="http://www.jsonx.org/schema-0.3.jsd [^ ]+", use=org.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String jx_3aSchemaLocation;

    public void setJx_3aSchemaLocation(final java.lang.String jx_3aSchemaLocation) {
      this.jx_3aSchemaLocation = jx_3aSchemaLocation;
    }

    public java.lang.String getJx_3aSchemaLocation() {
      return jx_3aSchemaLocation;
    }

    @org.jsonx.AnyProperty(name="[a-zA-Z_][-a-zA-Z\\d_]*", types={@org.jsonx.t(objects=org.jsonx.schema.Array.class), @org.jsonx.t(objects=org.jsonx.schema.Boolean.class), @org.jsonx.t(objects=org.jsonx.schema.Number.class), @org.jsonx.t(objects=org.jsonx.schema.ObjectType.class), @org.jsonx.t(objects=org.jsonx.schema.String.class)}, nullable=false)
    public final java.util.LinkedHashMap<java.lang.String,java.lang.Object> _5ba_2dZA_2dZ___5d_5b_2dA_2dZA_2dZ_5cD___5d_2a = new java.util.LinkedHashMap<>();

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.Schema) || !super.equals(obj))
        return false;

      final org.jsonx.schema.Schema that = (org.jsonx.schema.Schema)obj;
      if (that.jx_3aNs != null ? !that.jx_3aNs.equals(jx_3aNs) : jx_3aNs != null)
        return false;

      if (that.jx_3aSchemaLocation != null ? !that.jx_3aSchemaLocation.equals(jx_3aSchemaLocation) : jx_3aSchemaLocation != null)
        return false;

      if (that._5ba_2dZA_2dZ___5d_5b_2dA_2dZA_2dZ_5cD___5d_2a != null ? !that._5ba_2dZA_2dZ___5d_5b_2dA_2dZA_2dZ_5cD___5d_2a.equals(_5ba_2dZA_2dZ___5d_5b_2dA_2dZA_2dZ_5cD___5d_2a) : _5ba_2dZA_2dZ___5d_5b_2dA_2dZA_2dZ_5cD___5d_2a != null)
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 1381524284 * 31 + super.hashCode();
      hashCode = 31 * hashCode + java.util.Objects.hashCode(jx_3aNs);
      hashCode = 31 * hashCode + java.util.Objects.hashCode(jx_3aSchemaLocation);
      hashCode = 31 * hashCode + java.util.Objects.hashCode(_5ba_2dZA_2dZ___5d_5b_2dA_2dZA_2dZ_5cD___5d_2a);
      return hashCode;
    }
  }

  public static class String extends org.jsonx.schema.Member {
    @org.jsonx.StringProperty(name="jx:type", pattern="string", nullable=false)
    private java.lang.String jx_3aType;

    public void setJx_3aType(final java.lang.String jx_3aType) {
      this.jx_3aType = jx_3aType;
    }

    public java.lang.String getJx_3aType() {
      return jx_3aType;
    }

    @org.jsonx.StringProperty(use=org.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String pattern;

    public void setPattern(final java.lang.String pattern) {
      this.pattern = pattern;
    }

    public java.lang.String getPattern() {
      return pattern;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.String) || !super.equals(obj))
        return false;

      final org.jsonx.schema.String that = (org.jsonx.schema.String)obj;
      if (that.jx_3aType != null ? !that.jx_3aType.equals(jx_3aType) : jx_3aType != null)
        return false;

      if (that.pattern != null ? !that.pattern.equals(pattern) : pattern != null)
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 1397525932 * 31 + super.hashCode();
      hashCode = 31 * hashCode + java.util.Objects.hashCode(jx_3aType);
      hashCode = 31 * hashCode + java.util.Objects.hashCode(pattern);
      return hashCode;
    }
  }

  public static class StringElement extends org.jsonx.schema.String {
    @org.jsonx.BooleanProperty(use=org.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.Boolean nullable;

    public void setNullable(final java.lang.Boolean nullable) {
      this.nullable = nullable;
    }

    public java.lang.Boolean getNullable() {
      return nullable;
    }

    @org.jsonx.StringProperty(pattern="[\\d]+", use=org.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String minOccurs;

    public void setMinOccurs(final java.lang.String minOccurs) {
      this.minOccurs = minOccurs;
    }

    public java.lang.String getMinOccurs() {
      return minOccurs;
    }

    @org.jsonx.StringProperty(pattern="([\\d]+|unbounded)", use=org.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String maxOccurs;

    public void setMaxOccurs(final java.lang.String maxOccurs) {
      this.maxOccurs = maxOccurs;
    }

    public java.lang.String getMaxOccurs() {
      return maxOccurs;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.StringElement) || !super.equals(obj))
        return false;

      final org.jsonx.schema.StringElement that = (org.jsonx.schema.StringElement)obj;
      if (that.nullable != null ? !that.nullable.equals(nullable) : nullable != null)
        return false;

      if (that.minOccurs != null ? !that.minOccurs.equals(minOccurs) : minOccurs != null)
        return false;

      if (that.maxOccurs != null ? !that.maxOccurs.equals(maxOccurs) : maxOccurs != null)
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 1884336048 * 31 + super.hashCode();
      hashCode = 31 * hashCode + java.util.Objects.hashCode(nullable);
      hashCode = 31 * hashCode + java.util.Objects.hashCode(minOccurs);
      hashCode = 31 * hashCode + java.util.Objects.hashCode(maxOccurs);
      return hashCode;
    }
  }

  public static class StringProperty extends org.jsonx.schema.String {
    @org.jsonx.BooleanProperty(use=org.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.Boolean nullable;

    public void setNullable(final java.lang.Boolean nullable) {
      this.nullable = nullable;
    }

    public java.lang.Boolean getNullable() {
      return nullable;
    }

    @org.jsonx.StringProperty(pattern="(required|optional)", use=org.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String use;

    public void setUse(final java.lang.String use) {
      this.use = use;
    }

    public java.lang.String getUse() {
      return use;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.schema.StringProperty) || !super.equals(obj))
        return false;

      final org.jsonx.schema.StringProperty that = (org.jsonx.schema.StringProperty)obj;
      if (that.nullable != null ? !that.nullable.equals(nullable) : nullable != null)
        return false;

      if (that.use != null ? !that.use.equals(use) : use != null)
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = 1595040673 * 31 + super.hashCode();
      hashCode = 31 * hashCode + java.util.Objects.hashCode(nullable);
      hashCode = 31 * hashCode + java.util.Objects.hashCode(use);
      return hashCode;
    }
  }
}