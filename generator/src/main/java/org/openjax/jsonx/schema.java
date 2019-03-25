package org.openjax.jsonx;

public class schema {
  public static abstract class Any extends org.openjax.jsonx.schema.Member {
    @org.openjax.jsonx.runtime.StringProperty(name="class", pattern="any", nullable=false)
    private java.lang.String _class;
    
    public void set0lass(final java.lang.String _class) {
      this._class = _class;
    }
    
    public java.lang.String get0lass() {
      return _class;
    }
    
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;
    
      if (!(obj instanceof org.openjax.jsonx.schema.Any) || !super.equals(obj))
        return false;
    
      final org.openjax.jsonx.schema.Any that = (org.openjax.jsonx.schema.Any)obj;
      if (that._class != null ? !that._class.equals(_class) : _class != null)
        return false;
    
      return true;
    }
    
    @java.lang.Override
    public int hashCode() {
      int hashCode = -1859646040 * 31 + super.hashCode();
      hashCode = 31 * hashCode + (_class == null ? 0 : _class.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.runtime.JxEncoder.get().marshal(this);
    }
  }

  public static class AnyElement extends org.openjax.jsonx.schema.Any {
    @org.openjax.jsonx.runtime.BooleanProperty(use=org.openjax.jsonx.runtime.Use.OPTIONAL, nullable=false)
    private java.lang.Boolean nullable;
    
    public void setNullable(final java.lang.Boolean nullable) {
      this.nullable = nullable;
    }
    
    public java.lang.Boolean getNullable() {
      return nullable;
    }
    
    @org.openjax.jsonx.runtime.StringProperty(pattern="[\\d]+", use=org.openjax.jsonx.runtime.Use.OPTIONAL, nullable=false)
    private java.lang.String minOccurs;
    
    public void setMinOccurs(final java.lang.String minOccurs) {
      this.minOccurs = minOccurs;
    }
    
    public java.lang.String getMinOccurs() {
      return minOccurs;
    }
    
    @org.openjax.jsonx.runtime.StringProperty(pattern="([\\d]+|unbounded)", use=org.openjax.jsonx.runtime.Use.OPTIONAL, nullable=false)
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
    
      if (!(obj instanceof org.openjax.jsonx.schema.AnyElement) || !super.equals(obj))
        return false;
    
      final org.openjax.jsonx.schema.AnyElement that = (org.openjax.jsonx.schema.AnyElement)obj;
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
      int hashCode = -318735308 * 31 + super.hashCode();
      hashCode = 31 * hashCode + (nullable == null ? 0 : nullable.hashCode());
      hashCode = 31 * hashCode + (minOccurs == null ? 0 : minOccurs.hashCode());
      hashCode = 31 * hashCode + (maxOccurs == null ? 0 : maxOccurs.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.runtime.JxEncoder.get().marshal(this);
    }
  }

  public static class AnyProperty extends org.openjax.jsonx.schema.Any {
    @org.openjax.jsonx.runtime.StringProperty(pattern=".*", nullable=false)
    private java.lang.String name;
    
    public void setName(final java.lang.String name) {
      this.name = name;
    }
    
    public java.lang.String getName() {
      return name;
    }
    
    @org.openjax.jsonx.runtime.StringProperty(pattern="(required|optional)", use=org.openjax.jsonx.runtime.Use.OPTIONAL, nullable=false)
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
    
      if (!(obj instanceof org.openjax.jsonx.schema.AnyProperty) || !super.equals(obj))
        return false;
    
      final org.openjax.jsonx.schema.AnyProperty that = (org.openjax.jsonx.schema.AnyProperty)obj;
      if (that.name != null ? !that.name.equals(name) : name != null)
        return false;
    
      if (that.use != null ? !that.use.equals(use) : use != null)
        return false;
    
      return true;
    }
    
    @java.lang.Override
    public int hashCode() {
      int hashCode = 2019305373 * 31 + super.hashCode();
      hashCode = 31 * hashCode + (name == null ? 0 : name.hashCode());
      hashCode = 31 * hashCode + (use == null ? 0 : use.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.runtime.JxEncoder.get().marshal(this);
    }
  }

  public static abstract class Array extends org.openjax.jsonx.schema.Member {
    @org.openjax.jsonx.runtime.StringProperty(name="class", pattern="array", nullable=false)
    private java.lang.String _class;
    
    public void set0lass(final java.lang.String _class) {
      this._class = _class;
    }
    
    public java.lang.String get0lass() {
      return _class;
    }
    
    @org.openjax.jsonx.runtime.StringProperty(pattern="[\\d]+", use=org.openjax.jsonx.runtime.Use.OPTIONAL, nullable=false)
    private java.lang.String minIterate;
    
    public void setMinIterate(final java.lang.String minIterate) {
      this.minIterate = minIterate;
    }
    
    public java.lang.String getMinIterate() {
      return minIterate;
    }
    
    @org.openjax.jsonx.runtime.StringProperty(pattern="([\\d]+|unbounded)", use=org.openjax.jsonx.runtime.Use.OPTIONAL, nullable=false)
    private java.lang.String maxIterate;
    
    public void setMaxIterate(final java.lang.String maxIterate) {
      this.maxIterate = maxIterate;
    }
    
    public java.lang.String getMaxIterate() {
      return maxIterate;
    }
    
    @org.openjax.jsonx.runtime.ObjectElement(id=5, type=org.openjax.jsonx.schema.ObjectElement.class, minOccurs=0, nullable=false)
    @org.openjax.jsonx.runtime.ObjectElement(id=4, type=org.openjax.jsonx.schema.StringElement.class, minOccurs=0, nullable=false)
    @org.openjax.jsonx.runtime.ObjectElement(id=3, type=org.openjax.jsonx.schema.ReferenceElement.class, minOccurs=0, nullable=false)
    @org.openjax.jsonx.runtime.ObjectElement(id=2, type=org.openjax.jsonx.schema.NumberElement.class, minOccurs=0, nullable=false)
    @org.openjax.jsonx.runtime.ObjectElement(id=1, type=org.openjax.jsonx.schema.BooleanElement.class, minOccurs=0, nullable=false)
    @org.openjax.jsonx.runtime.ObjectElement(id=0, type=org.openjax.jsonx.schema.ArrayElement.class, minOccurs=0, nullable=false)
    @org.openjax.jsonx.runtime.ArrayProperty(elementIds={0, 1, 2, 3, 4, 5}, maxIterate=2147483647, nullable=false)
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
    
      if (!(obj instanceof org.openjax.jsonx.schema.Array) || !super.equals(obj))
        return false;
    
      final org.openjax.jsonx.schema.Array that = (org.openjax.jsonx.schema.Array)obj;
      if (that._class != null ? !that._class.equals(_class) : _class != null)
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
      int hashCode = -413333739 * 31 + super.hashCode();
      hashCode = 31 * hashCode + (_class == null ? 0 : _class.hashCode());
      hashCode = 31 * hashCode + (minIterate == null ? 0 : minIterate.hashCode());
      hashCode = 31 * hashCode + (maxIterate == null ? 0 : maxIterate.hashCode());
      hashCode = 31 * hashCode + (elements == null ? 0 : elements.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.runtime.JxEncoder.get().marshal(this);
    }
  }

  public static class ArrayElement extends org.openjax.jsonx.schema.Array {
    @org.openjax.jsonx.runtime.BooleanProperty(use=org.openjax.jsonx.runtime.Use.OPTIONAL, nullable=false)
    private java.lang.Boolean nullable;
    
    public void setNullable(final java.lang.Boolean nullable) {
      this.nullable = nullable;
    }
    
    public java.lang.Boolean getNullable() {
      return nullable;
    }
    
    @org.openjax.jsonx.runtime.StringProperty(pattern="[\\d]+", use=org.openjax.jsonx.runtime.Use.OPTIONAL, nullable=false)
    private java.lang.String minOccurs;
    
    public void setMinOccurs(final java.lang.String minOccurs) {
      this.minOccurs = minOccurs;
    }
    
    public java.lang.String getMinOccurs() {
      return minOccurs;
    }
    
    @org.openjax.jsonx.runtime.StringProperty(pattern="([\\d]+|unbounded)", use=org.openjax.jsonx.runtime.Use.OPTIONAL, nullable=false)
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
    
      if (!(obj instanceof org.openjax.jsonx.schema.ArrayElement) || !super.equals(obj))
        return false;
    
      final org.openjax.jsonx.schema.ArrayElement that = (org.openjax.jsonx.schema.ArrayElement)obj;
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
      int hashCode = 869367335 * 31 + super.hashCode();
      hashCode = 31 * hashCode + (nullable == null ? 0 : nullable.hashCode());
      hashCode = 31 * hashCode + (minOccurs == null ? 0 : minOccurs.hashCode());
      hashCode = 31 * hashCode + (maxOccurs == null ? 0 : maxOccurs.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.runtime.JxEncoder.get().marshal(this);
    }
  }

  public static class ArrayProperty extends org.openjax.jsonx.schema.ArrayType {
    @org.openjax.jsonx.runtime.StringProperty(pattern="(required|optional)", use=org.openjax.jsonx.runtime.Use.OPTIONAL, nullable=false)
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
    
      if (!(obj instanceof org.openjax.jsonx.schema.ArrayProperty) || !super.equals(obj))
        return false;
    
      final org.openjax.jsonx.schema.ArrayProperty that = (org.openjax.jsonx.schema.ArrayProperty)obj;
      if (that.use != null ? !that.use.equals(use) : use != null)
        return false;
    
      return true;
    }
    
    @java.lang.Override
    public int hashCode() {
      int hashCode = 195781642 * 31 + super.hashCode();
      hashCode = 31 * hashCode + (use == null ? 0 : use.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.runtime.JxEncoder.get().marshal(this);
    }
  }

  public static class ArrayType extends org.openjax.jsonx.schema.Array {
    @org.openjax.jsonx.runtime.StringProperty(pattern=".*", nullable=false)
    private java.lang.String name;
    
    public void setName(final java.lang.String name) {
      this.name = name;
    }
    
    public java.lang.String getName() {
      return name;
    }
    
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;
    
      if (!(obj instanceof org.openjax.jsonx.schema.ArrayType) || !super.equals(obj))
        return false;
    
      final org.openjax.jsonx.schema.ArrayType that = (org.openjax.jsonx.schema.ArrayType)obj;
      if (that.name != null ? !that.name.equals(name) : name != null)
        return false;
    
      return true;
    }
    
    @java.lang.Override
    public int hashCode() {
      int hashCode = 1423013871 * 31 + super.hashCode();
      hashCode = 31 * hashCode + (name == null ? 0 : name.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.runtime.JxEncoder.get().marshal(this);
    }
  }

  public static abstract class Boolean extends org.openjax.jsonx.schema.Member {
    @org.openjax.jsonx.runtime.StringProperty(name="class", pattern="boolean", nullable=false)
    private java.lang.String _class;
    
    public void set0lass(final java.lang.String _class) {
      this._class = _class;
    }
    
    public java.lang.String get0lass() {
      return _class;
    }
    
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;
    
      if (!(obj instanceof org.openjax.jsonx.schema.Boolean) || !super.equals(obj))
        return false;
    
      final org.openjax.jsonx.schema.Boolean that = (org.openjax.jsonx.schema.Boolean)obj;
      if (that._class != null ? !that._class.equals(_class) : _class != null)
        return false;
    
      return true;
    }
    
    @java.lang.Override
    public int hashCode() {
      int hashCode = -1277574684 * 31 + super.hashCode();
      hashCode = 31 * hashCode + (_class == null ? 0 : _class.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.runtime.JxEncoder.get().marshal(this);
    }
  }

  public static class BooleanElement extends org.openjax.jsonx.schema.Boolean {
    @org.openjax.jsonx.runtime.BooleanProperty(use=org.openjax.jsonx.runtime.Use.OPTIONAL, nullable=false)
    private java.lang.Boolean nullable;
    
    public void setNullable(final java.lang.Boolean nullable) {
      this.nullable = nullable;
    }
    
    public java.lang.Boolean getNullable() {
      return nullable;
    }
    
    @org.openjax.jsonx.runtime.StringProperty(pattern="[\\d]+", use=org.openjax.jsonx.runtime.Use.OPTIONAL, nullable=false)
    private java.lang.String minOccurs;
    
    public void setMinOccurs(final java.lang.String minOccurs) {
      this.minOccurs = minOccurs;
    }
    
    public java.lang.String getMinOccurs() {
      return minOccurs;
    }
    
    @org.openjax.jsonx.runtime.StringProperty(pattern="([\\d]+|unbounded)", use=org.openjax.jsonx.runtime.Use.OPTIONAL, nullable=false)
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
    
      if (!(obj instanceof org.openjax.jsonx.schema.BooleanElement) || !super.equals(obj))
        return false;
    
      final org.openjax.jsonx.schema.BooleanElement that = (org.openjax.jsonx.schema.BooleanElement)obj;
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
      int hashCode = 1031982200 * 31 + super.hashCode();
      hashCode = 31 * hashCode + (nullable == null ? 0 : nullable.hashCode());
      hashCode = 31 * hashCode + (minOccurs == null ? 0 : minOccurs.hashCode());
      hashCode = 31 * hashCode + (maxOccurs == null ? 0 : maxOccurs.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.runtime.JxEncoder.get().marshal(this);
    }
  }

  public static class BooleanProperty extends org.openjax.jsonx.schema.BooleanType {
    @org.openjax.jsonx.runtime.StringProperty(pattern="(required|optional)", use=org.openjax.jsonx.runtime.Use.OPTIONAL, nullable=false)
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
    
      if (!(obj instanceof org.openjax.jsonx.schema.BooleanProperty) || !super.equals(obj))
        return false;
    
      final org.openjax.jsonx.schema.BooleanProperty that = (org.openjax.jsonx.schema.BooleanProperty)obj;
      if (that.use != null ? !that.use.equals(use) : use != null)
        return false;
    
      return true;
    }
    
    @java.lang.Override
    public int hashCode() {
      int hashCode = 941875161 * 31 + super.hashCode();
      hashCode = 31 * hashCode + (use == null ? 0 : use.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.runtime.JxEncoder.get().marshal(this);
    }
  }

  public static class BooleanType extends org.openjax.jsonx.schema.Boolean {
    @org.openjax.jsonx.runtime.StringProperty(pattern=".*", nullable=false)
    private java.lang.String name;
    
    public void setName(final java.lang.String name) {
      this.name = name;
    }
    
    public java.lang.String getName() {
      return name;
    }
    
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;
    
      if (!(obj instanceof org.openjax.jsonx.schema.BooleanType) || !super.equals(obj))
        return false;
    
      final org.openjax.jsonx.schema.BooleanType that = (org.openjax.jsonx.schema.BooleanType)obj;
      if (that.name != null ? !that.name.equals(name) : name != null)
        return false;
    
      return true;
    }
    
    @java.lang.Override
    public int hashCode() {
      int hashCode = -876203202 * 31 + super.hashCode();
      hashCode = 31 * hashCode + (name == null ? 0 : name.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.runtime.JxEncoder.get().marshal(this);
    }
  }

  public static abstract class Member implements org.openjax.jsonx.runtime.JxObject {
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;
    
      if (!(obj instanceof org.openjax.jsonx.schema.Member))
        return false;
    
      return true;
    }
    
    @java.lang.Override
    public int hashCode() {
      return 402951518;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.runtime.JxEncoder.get().marshal(this);
    }
  }

  public static abstract class Number extends org.openjax.jsonx.schema.Member {
    @org.openjax.jsonx.runtime.StringProperty(name="class", pattern="number", nullable=false)
    private java.lang.String _class;
    
    public void set0lass(final java.lang.String _class) {
      this._class = _class;
    }
    
    public java.lang.String get0lass() {
      return _class;
    }
    
    @org.openjax.jsonx.runtime.StringProperty(pattern="(integer|real)", use=org.openjax.jsonx.runtime.Use.OPTIONAL, nullable=false)
    private java.lang.String form;
    
    public void setForm(final java.lang.String form) {
      this.form = form;
    }
    
    public java.lang.String getForm() {
      return form;
    }
    
    @org.openjax.jsonx.runtime.StringProperty(pattern="[\\(\\[](-?\\d+(\\.\\d+)?)?,(-?\\d+(\\.\\d+)?)?[\\)\\]]", use=org.openjax.jsonx.runtime.Use.OPTIONAL, nullable=false)
    private java.lang.String range;
    
    public void setRange(final java.lang.String range) {
      this.range = range;
    }
    
    public java.lang.String getRange() {
      return range;
    }
    
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;
    
      if (!(obj instanceof org.openjax.jsonx.schema.Number) || !super.equals(obj))
        return false;
    
      final org.openjax.jsonx.schema.Number that = (org.openjax.jsonx.schema.Number)obj;
      if (that._class != null ? !that._class.equals(_class) : _class != null)
        return false;
    
      if (that.form != null ? !that.form.equals(form) : form != null)
        return false;
    
      if (that.range != null ? !that.range.equals(range) : range != null)
        return false;
    
      return true;
    }
    
    @java.lang.Override
    public int hashCode() {
      int hashCode = 446357005 * 31 + super.hashCode();
      hashCode = 31 * hashCode + (_class == null ? 0 : _class.hashCode());
      hashCode = 31 * hashCode + (form == null ? 0 : form.hashCode());
      hashCode = 31 * hashCode + (range == null ? 0 : range.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.runtime.JxEncoder.get().marshal(this);
    }
  }

  public static class NumberElement extends org.openjax.jsonx.schema.Number {
    @org.openjax.jsonx.runtime.BooleanProperty(use=org.openjax.jsonx.runtime.Use.OPTIONAL, nullable=false)
    private java.lang.Boolean nullable;
    
    public void setNullable(final java.lang.Boolean nullable) {
      this.nullable = nullable;
    }
    
    public java.lang.Boolean getNullable() {
      return nullable;
    }
    
    @org.openjax.jsonx.runtime.StringProperty(pattern="[\\d]+", use=org.openjax.jsonx.runtime.Use.OPTIONAL, nullable=false)
    private java.lang.String minOccurs;
    
    public void setMinOccurs(final java.lang.String minOccurs) {
      this.minOccurs = minOccurs;
    }
    
    public java.lang.String getMinOccurs() {
      return minOccurs;
    }
    
    @org.openjax.jsonx.runtime.StringProperty(pattern="([\\d]+|unbounded)", use=org.openjax.jsonx.runtime.Use.OPTIONAL, nullable=false)
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
    
      if (!(obj instanceof org.openjax.jsonx.schema.NumberElement) || !super.equals(obj))
        return false;
    
      final org.openjax.jsonx.schema.NumberElement that = (org.openjax.jsonx.schema.NumberElement)obj;
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
      int hashCode = 798646319 * 31 + super.hashCode();
      hashCode = 31 * hashCode + (nullable == null ? 0 : nullable.hashCode());
      hashCode = 31 * hashCode + (minOccurs == null ? 0 : minOccurs.hashCode());
      hashCode = 31 * hashCode + (maxOccurs == null ? 0 : maxOccurs.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.runtime.JxEncoder.get().marshal(this);
    }
  }

  public static class NumberProperty extends org.openjax.jsonx.schema.NumberType {
    @org.openjax.jsonx.runtime.StringProperty(pattern="(required|optional)", use=org.openjax.jsonx.runtime.Use.OPTIONAL, nullable=false)
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
    
      if (!(obj instanceof org.openjax.jsonx.schema.NumberProperty) || !super.equals(obj))
        return false;
    
      final org.openjax.jsonx.schema.NumberProperty that = (org.openjax.jsonx.schema.NumberProperty)obj;
      if (that.use != null ? !that.use.equals(use) : use != null)
        return false;
    
      return true;
    }
    
    @java.lang.Override
    public int hashCode() {
      int hashCode = -1996569854 * 31 + super.hashCode();
      hashCode = 31 * hashCode + (use == null ? 0 : use.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.runtime.JxEncoder.get().marshal(this);
    }
  }

  public static class NumberType extends org.openjax.jsonx.schema.Number {
    @org.openjax.jsonx.runtime.StringProperty(pattern=".*", nullable=false)
    private java.lang.String name;
    
    public void setName(final java.lang.String name) {
      this.name = name;
    }
    
    public java.lang.String getName() {
      return name;
    }
    
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;
    
      if (!(obj instanceof org.openjax.jsonx.schema.NumberType) || !super.equals(obj))
        return false;
    
      final org.openjax.jsonx.schema.NumberType that = (org.openjax.jsonx.schema.NumberType)obj;
      if (that.name != null ? !that.name.equals(name) : name != null)
        return false;
    
      return true;
    }
    
    @java.lang.Override
    public int hashCode() {
      int hashCode = 1994068711 * 31 + super.hashCode();
      hashCode = 31 * hashCode + (name == null ? 0 : name.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.runtime.JxEncoder.get().marshal(this);
    }
  }

  public static abstract class Object extends org.openjax.jsonx.schema.Member {
    public static class Properties implements org.openjax.jsonx.runtime.JxObject {
      @org.openjax.jsonx.runtime.AnyProperty(name=".*", types={@org.openjax.jsonx.runtime.t(objects=org.openjax.jsonx.schema.ArrayProperty.class), @org.openjax.jsonx.runtime.t(objects=org.openjax.jsonx.schema.BooleanProperty.class), @org.openjax.jsonx.runtime.t(objects=org.openjax.jsonx.schema.NumberProperty.class), @org.openjax.jsonx.runtime.t(objects=org.openjax.jsonx.schema.ObjectProperty.class), @org.openjax.jsonx.runtime.t(objects=org.openjax.jsonx.schema.ReferenceProperty.class), @org.openjax.jsonx.runtime.t(objects=org.openjax.jsonx.schema.StringProperty.class)}, nullable=false)
      public final java.util.LinkedHashMap<java.lang.String,java.lang.Object> _2e_2a = new java.util.LinkedHashMap<>();
      
      @java.lang.Override
      public boolean equals(final java.lang.Object obj) {
        if (obj == this)
          return true;
      
        if (!(obj instanceof org.openjax.jsonx.schema.Object.Properties))
          return false;
      
        final org.openjax.jsonx.schema.Object.Properties that = (org.openjax.jsonx.schema.Object.Properties)obj;
        if (that._2e_2a != null ? !that._2e_2a.equals(_2e_2a) : _2e_2a != null)
          return false;
      
        return true;
      }
      
      @java.lang.Override
      public int hashCode() {
        int hashCode = 529995412;
        hashCode = 31 * hashCode + (_2e_2a == null ? 0 : _2e_2a.hashCode());
        return hashCode;
      }
      
      @java.lang.Override
      public java.lang.String toString() {
        return org.openjax.jsonx.runtime.JxEncoder.get().marshal(this);
      }
    }
    @org.openjax.jsonx.runtime.StringProperty(name="class", pattern="object", nullable=false)
    private java.lang.String _class;
    
    public void set0lass(final java.lang.String _class) {
      this._class = _class;
    }
    
    public java.lang.String get0lass() {
      return _class;
    }
    
    @org.openjax.jsonx.runtime.StringProperty(name="extends", pattern=".*", nullable=false)
    private java.lang.String _extends;
    
    public void setExtends(final java.lang.String _extends) {
      this._extends = _extends;
    }
    
    public java.lang.String getExtends() {
      return _extends;
    }
    
    @org.openjax.jsonx.runtime.ObjectProperty(nullable=false)
    private org.openjax.jsonx.schema.Object.Properties properties;
    
    public void setProperties(final org.openjax.jsonx.schema.Object.Properties properties) {
      this.properties = properties;
    }
    
    public org.openjax.jsonx.schema.Object.Properties getProperties() {
      return properties;
    }
    
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;
    
      if (!(obj instanceof org.openjax.jsonx.schema.Object) || !super.equals(obj))
        return false;
    
      final org.openjax.jsonx.schema.Object that = (org.openjax.jsonx.schema.Object)obj;
      if (that._class != null ? !that._class.equals(_class) : _class != null)
        return false;
    
      if (that._extends != null ? !that._extends.equals(_extends) : _extends != null)
        return false;
    
      if (that.properties != null ? !that.properties.equals(properties) : properties != null)
        return false;
    
      return true;
    }
    
    @java.lang.Override
    public int hashCode() {
      int hashCode = 457352707 * 31 + super.hashCode();
      hashCode = 31 * hashCode + (_class == null ? 0 : _class.hashCode());
      hashCode = 31 * hashCode + (_extends == null ? 0 : _extends.hashCode());
      hashCode = 31 * hashCode + (properties == null ? 0 : properties.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.runtime.JxEncoder.get().marshal(this);
    }
  }

  public static class ObjectElement extends org.openjax.jsonx.schema.Object {
    @org.openjax.jsonx.runtime.BooleanProperty(use=org.openjax.jsonx.runtime.Use.OPTIONAL, nullable=false)
    private java.lang.Boolean nullable;
    
    public void setNullable(final java.lang.Boolean nullable) {
      this.nullable = nullable;
    }
    
    public java.lang.Boolean getNullable() {
      return nullable;
    }
    
    @org.openjax.jsonx.runtime.StringProperty(pattern="[\\d]+", use=org.openjax.jsonx.runtime.Use.OPTIONAL, nullable=false)
    private java.lang.String minOccurs;
    
    public void setMinOccurs(final java.lang.String minOccurs) {
      this.minOccurs = minOccurs;
    }
    
    public java.lang.String getMinOccurs() {
      return minOccurs;
    }
    
    @org.openjax.jsonx.runtime.StringProperty(pattern="([\\d]+|unbounded)", use=org.openjax.jsonx.runtime.Use.OPTIONAL, nullable=false)
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
    
      if (!(obj instanceof org.openjax.jsonx.schema.ObjectElement) || !super.equals(obj))
        return false;
    
      final org.openjax.jsonx.schema.ObjectElement that = (org.openjax.jsonx.schema.ObjectElement)obj;
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
      int hashCode = 1364580217 * 31 + super.hashCode();
      hashCode = 31 * hashCode + (nullable == null ? 0 : nullable.hashCode());
      hashCode = 31 * hashCode + (minOccurs == null ? 0 : minOccurs.hashCode());
      hashCode = 31 * hashCode + (maxOccurs == null ? 0 : maxOccurs.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.runtime.JxEncoder.get().marshal(this);
    }
  }

  public static class ObjectProperty extends org.openjax.jsonx.schema.Object {
    @org.openjax.jsonx.runtime.StringProperty(pattern=".*", nullable=false)
    private java.lang.String name;
    
    public void setName(final java.lang.String name) {
      this.name = name;
    }
    
    public java.lang.String getName() {
      return name;
    }
    
    @org.openjax.jsonx.runtime.StringProperty(pattern="(required|optional)", use=org.openjax.jsonx.runtime.Use.OPTIONAL, nullable=false)
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
    
      if (!(obj instanceof org.openjax.jsonx.schema.ObjectProperty) || !super.equals(obj))
        return false;
    
      final org.openjax.jsonx.schema.ObjectProperty that = (org.openjax.jsonx.schema.ObjectProperty)obj;
      if (that.name != null ? !that.name.equals(name) : name != null)
        return false;
    
      if (that.use != null ? !that.use.equals(use) : use != null)
        return false;
    
      return true;
    }
    
    @java.lang.Override
    public int hashCode() {
      int hashCode = -1632488200 * 31 + super.hashCode();
      hashCode = 31 * hashCode + (name == null ? 0 : name.hashCode());
      hashCode = 31 * hashCode + (use == null ? 0 : use.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.runtime.JxEncoder.get().marshal(this);
    }
  }

  public static class ObjectType extends org.openjax.jsonx.schema.Object {
    @org.openjax.jsonx.runtime.StringProperty(pattern=".*", nullable=false)
    private java.lang.String name;
    
    public void setName(final java.lang.String name) {
      this.name = name;
    }
    
    public java.lang.String getName() {
      return name;
    }
    
    @org.openjax.jsonx.runtime.BooleanProperty(name="abstract", use=org.openjax.jsonx.runtime.Use.OPTIONAL, nullable=false)
    private java.lang.Boolean _abstract;
    
    public void setAbstract(final java.lang.Boolean _abstract) {
      this._abstract = _abstract;
    }
    
    public java.lang.Boolean getAbstract() {
      return _abstract;
    }
    
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;
    
      if (!(obj instanceof org.openjax.jsonx.schema.ObjectType) || !super.equals(obj))
        return false;
    
      final org.openjax.jsonx.schema.ObjectType that = (org.openjax.jsonx.schema.ObjectType)obj;
      if (that.name != null ? !that.name.equals(name) : name != null)
        return false;
    
      if (that._abstract != null ? !that._abstract.equals(_abstract) : _abstract != null)
        return false;
    
      return true;
    }
    
    @java.lang.Override
    public int hashCode() {
      int hashCode = -841879587 * 31 + super.hashCode();
      hashCode = 31 * hashCode + (name == null ? 0 : name.hashCode());
      hashCode = 31 * hashCode + (_abstract == null ? 0 : _abstract.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.runtime.JxEncoder.get().marshal(this);
    }
  }

  public static abstract class Reference extends org.openjax.jsonx.schema.Member {
    @org.openjax.jsonx.runtime.StringProperty(name="class", pattern="reference", nullable=false)
    private java.lang.String _class;
    
    public void set0lass(final java.lang.String _class) {
      this._class = _class;
    }
    
    public java.lang.String get0lass() {
      return _class;
    }
    
    @org.openjax.jsonx.runtime.StringProperty(pattern=".*", nullable=false)
    private java.lang.String type;
    
    public void setType(final java.lang.String type) {
      this.type = type;
    }
    
    public java.lang.String getType() {
      return type;
    }
    
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;
    
      if (!(obj instanceof org.openjax.jsonx.schema.Reference) || !super.equals(obj))
        return false;
    
      final org.openjax.jsonx.schema.Reference that = (org.openjax.jsonx.schema.Reference)obj;
      if (that._class != null ? !that._class.equals(_class) : _class != null)
        return false;
    
      if (that.type != null ? !that.type.equals(type) : type != null)
        return false;
    
      return true;
    }
    
    @java.lang.Override
    public int hashCode() {
      int hashCode = 1922766343 * 31 + super.hashCode();
      hashCode = 31 * hashCode + (_class == null ? 0 : _class.hashCode());
      hashCode = 31 * hashCode + (type == null ? 0 : type.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.runtime.JxEncoder.get().marshal(this);
    }
  }

  public static class ReferenceElement extends org.openjax.jsonx.schema.Reference {
    @org.openjax.jsonx.runtime.BooleanProperty(use=org.openjax.jsonx.runtime.Use.OPTIONAL, nullable=false)
    private java.lang.Boolean nullable;
    
    public void setNullable(final java.lang.Boolean nullable) {
      this.nullable = nullable;
    }
    
    public java.lang.Boolean getNullable() {
      return nullable;
    }
    
    @org.openjax.jsonx.runtime.StringProperty(pattern="[\\d]+", use=org.openjax.jsonx.runtime.Use.OPTIONAL, nullable=false)
    private java.lang.String minOccurs;
    
    public void setMinOccurs(final java.lang.String minOccurs) {
      this.minOccurs = minOccurs;
    }
    
    public java.lang.String getMinOccurs() {
      return minOccurs;
    }
    
    @org.openjax.jsonx.runtime.StringProperty(pattern="([\\d]+|unbounded)", use=org.openjax.jsonx.runtime.Use.OPTIONAL, nullable=false)
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
    
      if (!(obj instanceof org.openjax.jsonx.schema.ReferenceElement) || !super.equals(obj))
        return false;
    
      final org.openjax.jsonx.schema.ReferenceElement that = (org.openjax.jsonx.schema.ReferenceElement)obj;
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
      int hashCode = -1440474379 * 31 + super.hashCode();
      hashCode = 31 * hashCode + (nullable == null ? 0 : nullable.hashCode());
      hashCode = 31 * hashCode + (minOccurs == null ? 0 : minOccurs.hashCode());
      hashCode = 31 * hashCode + (maxOccurs == null ? 0 : maxOccurs.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.runtime.JxEncoder.get().marshal(this);
    }
  }

  public static class ReferenceProperty extends org.openjax.jsonx.schema.ReferenceType {
    @org.openjax.jsonx.runtime.StringProperty(pattern="(required|optional)", use=org.openjax.jsonx.runtime.Use.OPTIONAL, nullable=false)
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
    
      if (!(obj instanceof org.openjax.jsonx.schema.ReferenceProperty) || !super.equals(obj))
        return false;
    
      final org.openjax.jsonx.schema.ReferenceProperty that = (org.openjax.jsonx.schema.ReferenceProperty)obj;
      if (that.use != null ? !that.use.equals(use) : use != null)
        return false;
    
      return true;
    }
    
    @java.lang.Override
    public int hashCode() {
      int hashCode = 1605132540 * 31 + super.hashCode();
      hashCode = 31 * hashCode + (use == null ? 0 : use.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.runtime.JxEncoder.get().marshal(this);
    }
  }

  public static abstract class ReferenceType extends org.openjax.jsonx.schema.Reference {
    @org.openjax.jsonx.runtime.StringProperty(pattern=".*", nullable=false)
    private java.lang.String name;
    
    public void setName(final java.lang.String name) {
      this.name = name;
    }
    
    public java.lang.String getName() {
      return name;
    }
    
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;
    
      if (!(obj instanceof org.openjax.jsonx.schema.ReferenceType) || !super.equals(obj))
        return false;
    
      final org.openjax.jsonx.schema.ReferenceType that = (org.openjax.jsonx.schema.ReferenceType)obj;
      if (that.name != null ? !that.name.equals(name) : name != null)
        return false;
    
      return true;
    }
    
    @java.lang.Override
    public int hashCode() {
      int hashCode = -475349535 * 31 + super.hashCode();
      hashCode = 31 * hashCode + (name == null ? 0 : name.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.runtime.JxEncoder.get().marshal(this);
    }
  }

  public static class Schema implements org.openjax.jsonx.runtime.JxObject {
    @org.openjax.jsonx.runtime.AnyProperty(name=".*", types={@org.openjax.jsonx.runtime.t(objects=org.openjax.jsonx.schema.ArrayType.class), @org.openjax.jsonx.runtime.t(objects=org.openjax.jsonx.schema.BooleanType.class), @org.openjax.jsonx.runtime.t(objects=org.openjax.jsonx.schema.NumberType.class), @org.openjax.jsonx.runtime.t(objects=org.openjax.jsonx.schema.ObjectType.class), @org.openjax.jsonx.runtime.t(objects=org.openjax.jsonx.schema.StringType.class)}, nullable=false)
    public final java.util.LinkedHashMap<java.lang.String,java.lang.Object> _2e_2a = new java.util.LinkedHashMap<>();
    
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;
    
      if (!(obj instanceof org.openjax.jsonx.schema.Schema))
        return false;
    
      final org.openjax.jsonx.schema.Schema that = (org.openjax.jsonx.schema.Schema)obj;
      if (that._2e_2a != null ? !that._2e_2a.equals(_2e_2a) : _2e_2a != null)
        return false;
    
      return true;
    }
    
    @java.lang.Override
    public int hashCode() {
      int hashCode = 572733541;
      hashCode = 31 * hashCode + (_2e_2a == null ? 0 : _2e_2a.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.runtime.JxEncoder.get().marshal(this);
    }
  }

  public static abstract class String extends org.openjax.jsonx.schema.Member {
    @org.openjax.jsonx.runtime.StringProperty(name="class", pattern="string", nullable=false)
    private java.lang.String _class;
    
    public void set0lass(final java.lang.String _class) {
      this._class = _class;
    }
    
    public java.lang.String get0lass() {
      return _class;
    }
    
    @org.openjax.jsonx.runtime.StringProperty(pattern=".*", use=org.openjax.jsonx.runtime.Use.OPTIONAL, nullable=false)
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
    
      if (!(obj instanceof org.openjax.jsonx.schema.String) || !super.equals(obj))
        return false;
    
      final org.openjax.jsonx.schema.String that = (org.openjax.jsonx.schema.String)obj;
      if (that._class != null ? !that._class.equals(_class) : _class != null)
        return false;
    
      if (that.pattern != null ? !that.pattern.equals(pattern) : pattern != null)
        return false;
    
      return true;
    }
    
    @java.lang.Override
    public int hashCode() {
      int hashCode = 588735189 * 31 + super.hashCode();
      hashCode = 31 * hashCode + (_class == null ? 0 : _class.hashCode());
      hashCode = 31 * hashCode + (pattern == null ? 0 : pattern.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.runtime.JxEncoder.get().marshal(this);
    }
  }

  public static class StringElement extends org.openjax.jsonx.schema.String {
    @org.openjax.jsonx.runtime.BooleanProperty(use=org.openjax.jsonx.runtime.Use.OPTIONAL, nullable=false)
    private java.lang.Boolean nullable;
    
    public void setNullable(final java.lang.Boolean nullable) {
      this.nullable = nullable;
    }
    
    public java.lang.Boolean getNullable() {
      return nullable;
    }
    
    @org.openjax.jsonx.runtime.StringProperty(pattern="[\\d]+", use=org.openjax.jsonx.runtime.Use.OPTIONAL, nullable=false)
    private java.lang.String minOccurs;
    
    public void setMinOccurs(final java.lang.String minOccurs) {
      this.minOccurs = minOccurs;
    }
    
    public java.lang.String getMinOccurs() {
      return minOccurs;
    }
    
    @org.openjax.jsonx.runtime.StringProperty(pattern="([\\d]+|unbounded)", use=org.openjax.jsonx.runtime.Use.OPTIONAL, nullable=false)
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
    
      if (!(obj instanceof org.openjax.jsonx.schema.StringElement) || !super.equals(obj))
        return false;
    
      final org.openjax.jsonx.schema.StringElement that = (org.openjax.jsonx.schema.StringElement)obj;
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
      int hashCode = 648833639 * 31 + super.hashCode();
      hashCode = 31 * hashCode + (nullable == null ? 0 : nullable.hashCode());
      hashCode = 31 * hashCode + (minOccurs == null ? 0 : minOccurs.hashCode());
      hashCode = 31 * hashCode + (maxOccurs == null ? 0 : maxOccurs.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.runtime.JxEncoder.get().marshal(this);
    }
  }

  public static class StringProperty extends org.openjax.jsonx.schema.StringType {
    @org.openjax.jsonx.runtime.StringProperty(pattern="(required|optional)", use=org.openjax.jsonx.runtime.Use.OPTIONAL, nullable=false)
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
    
      if (!(obj instanceof org.openjax.jsonx.schema.StringProperty) || !super.equals(obj))
        return false;
    
      final org.openjax.jsonx.schema.StringProperty that = (org.openjax.jsonx.schema.StringProperty)obj;
      if (that.use != null ? !that.use.equals(use) : use != null)
        return false;
    
      return true;
    }
    
    @java.lang.Override
    public int hashCode() {
      int hashCode = 1949171658 * 31 + super.hashCode();
      hashCode = 31 * hashCode + (use == null ? 0 : use.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.runtime.JxEncoder.get().marshal(this);
    }
  }

  public static class StringType extends org.openjax.jsonx.schema.String {
    @org.openjax.jsonx.runtime.StringProperty(pattern=".*", nullable=false)
    private java.lang.String name;
    
    public void setName(final java.lang.String name) {
      this.name = name;
    }
    
    public java.lang.String getName() {
      return name;
    }
    
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;
    
      if (!(obj instanceof org.openjax.jsonx.schema.StringType) || !super.equals(obj))
        return false;
    
      final org.openjax.jsonx.schema.StringType that = (org.openjax.jsonx.schema.StringType)obj;
      if (that.name != null ? !that.name.equals(name) : name != null)
        return false;
    
      return true;
    }
    
    @java.lang.Override
    public int hashCode() {
      int hashCode = 813167535 * 31 + super.hashCode();
      hashCode = 31 * hashCode + (name == null ? 0 : name.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.runtime.JxEncoder.get().marshal(this);
    }
  }
}