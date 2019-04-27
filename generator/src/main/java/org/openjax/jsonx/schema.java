package org.openjax.jsonx;

public class schema {
  public static class Any extends org.openjax.jsonx.schema.Member {
    @org.openjax.jsonx.StringProperty(name="jsd:class", pattern="any", nullable=false)
    private java.lang.String jsd_3aClass;
    
    public void setJsd_3aClass(final java.lang.String jsd_3aClass) {
      this.jsd_3aClass = jsd_3aClass;
    }
    
    public java.lang.String getJsd_3aClass() {
      return jsd_3aClass;
    }
    
    @org.openjax.jsonx.StringProperty(name="jsd:types", pattern="\\S+( \\S+)*", use=org.openjax.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String jsd_3aTypes;
    
    public void setJsd_3aTypes(final java.lang.String jsd_3aTypes) {
      this.jsd_3aTypes = jsd_3aTypes;
    }
    
    public java.lang.String getJsd_3aTypes() {
      return jsd_3aTypes;
    }
    
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;
    
      if (!(obj instanceof org.openjax.jsonx.schema.Any) || !super.equals(obj))
        return false;
    
      final org.openjax.jsonx.schema.Any that = (org.openjax.jsonx.schema.Any)obj;
      if (that.jsd_3aClass != null ? !that.jsd_3aClass.equals(jsd_3aClass) : jsd_3aClass != null)
        return false;
    
      if (that.jsd_3aTypes != null ? !that.jsd_3aTypes.equals(jsd_3aTypes) : jsd_3aTypes != null)
        return false;
    
      return true;
    }
    
    @java.lang.Override
    public int hashCode() {
      int hashCode = -1859646040 * 31 + super.hashCode();
      hashCode = 31 * hashCode + (jsd_3aClass == null ? 0 : jsd_3aClass.hashCode());
      hashCode = 31 * hashCode + (jsd_3aTypes == null ? 0 : jsd_3aTypes.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.JxEncoder.get().marshal(this);
    }
  }

  public static class AnyElement extends org.openjax.jsonx.schema.Any {
    @org.openjax.jsonx.BooleanProperty(name="jsd:nullable", use=org.openjax.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.Boolean jsd_3aNullable;
    
    public void setJsd_3aNullable(final java.lang.Boolean jsd_3aNullable) {
      this.jsd_3aNullable = jsd_3aNullable;
    }
    
    public java.lang.Boolean getJsd_3aNullable() {
      return jsd_3aNullable;
    }
    
    @org.openjax.jsonx.StringProperty(name="jsd:minOccurs", pattern="[\\d]+", use=org.openjax.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String jsd_3aMinOccurs;
    
    public void setJsd_3aMinOccurs(final java.lang.String jsd_3aMinOccurs) {
      this.jsd_3aMinOccurs = jsd_3aMinOccurs;
    }
    
    public java.lang.String getJsd_3aMinOccurs() {
      return jsd_3aMinOccurs;
    }
    
    @org.openjax.jsonx.StringProperty(name="jsd:maxOccurs", pattern="([\\d]+|unbounded)", use=org.openjax.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String jsd_3aMaxOccurs;
    
    public void setJsd_3aMaxOccurs(final java.lang.String jsd_3aMaxOccurs) {
      this.jsd_3aMaxOccurs = jsd_3aMaxOccurs;
    }
    
    public java.lang.String getJsd_3aMaxOccurs() {
      return jsd_3aMaxOccurs;
    }
    
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;
    
      if (!(obj instanceof org.openjax.jsonx.schema.AnyElement) || !super.equals(obj))
        return false;
    
      final org.openjax.jsonx.schema.AnyElement that = (org.openjax.jsonx.schema.AnyElement)obj;
      if (that.jsd_3aNullable != null ? !that.jsd_3aNullable.equals(jsd_3aNullable) : jsd_3aNullable != null)
        return false;
    
      if (that.jsd_3aMinOccurs != null ? !that.jsd_3aMinOccurs.equals(jsd_3aMinOccurs) : jsd_3aMinOccurs != null)
        return false;
    
      if (that.jsd_3aMaxOccurs != null ? !that.jsd_3aMaxOccurs.equals(jsd_3aMaxOccurs) : jsd_3aMaxOccurs != null)
        return false;
    
      return true;
    }
    
    @java.lang.Override
    public int hashCode() {
      int hashCode = -318735308 * 31 + super.hashCode();
      hashCode = 31 * hashCode + (jsd_3aNullable == null ? 0 : jsd_3aNullable.hashCode());
      hashCode = 31 * hashCode + (jsd_3aMinOccurs == null ? 0 : jsd_3aMinOccurs.hashCode());
      hashCode = 31 * hashCode + (jsd_3aMaxOccurs == null ? 0 : jsd_3aMaxOccurs.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.JxEncoder.get().marshal(this);
    }
  }

  public static class AnyProperty extends org.openjax.jsonx.schema.Any {
    @org.openjax.jsonx.BooleanProperty(name="jsd:nullable", use=org.openjax.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.Boolean jsd_3aNullable;
    
    public void setJsd_3aNullable(final java.lang.Boolean jsd_3aNullable) {
      this.jsd_3aNullable = jsd_3aNullable;
    }
    
    public java.lang.Boolean getJsd_3aNullable() {
      return jsd_3aNullable;
    }
    
    @org.openjax.jsonx.StringProperty(name="jsd:use", pattern="(required|optional)", use=org.openjax.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String jsd_3aUse;
    
    public void setJsd_3aUse(final java.lang.String jsd_3aUse) {
      this.jsd_3aUse = jsd_3aUse;
    }
    
    public java.lang.String getJsd_3aUse() {
      return jsd_3aUse;
    }
    
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;
    
      if (!(obj instanceof org.openjax.jsonx.schema.AnyProperty) || !super.equals(obj))
        return false;
    
      final org.openjax.jsonx.schema.AnyProperty that = (org.openjax.jsonx.schema.AnyProperty)obj;
      if (that.jsd_3aNullable != null ? !that.jsd_3aNullable.equals(jsd_3aNullable) : jsd_3aNullable != null)
        return false;
    
      if (that.jsd_3aUse != null ? !that.jsd_3aUse.equals(jsd_3aUse) : jsd_3aUse != null)
        return false;
    
      return true;
    }
    
    @java.lang.Override
    public int hashCode() {
      int hashCode = 2019305373 * 31 + super.hashCode();
      hashCode = 31 * hashCode + (jsd_3aNullable == null ? 0 : jsd_3aNullable.hashCode());
      hashCode = 31 * hashCode + (jsd_3aUse == null ? 0 : jsd_3aUse.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.JxEncoder.get().marshal(this);
    }
  }

  public static class Array extends org.openjax.jsonx.schema.Member {
    @org.openjax.jsonx.StringProperty(name="jsd:class", pattern="array", nullable=false)
    private java.lang.String jsd_3aClass;
    
    public void setJsd_3aClass(final java.lang.String jsd_3aClass) {
      this.jsd_3aClass = jsd_3aClass;
    }
    
    public java.lang.String getJsd_3aClass() {
      return jsd_3aClass;
    }
    
    @org.openjax.jsonx.StringProperty(name="jsd:minIterate", pattern="[\\d]+", use=org.openjax.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String jsd_3aMinIterate;
    
    public void setJsd_3aMinIterate(final java.lang.String jsd_3aMinIterate) {
      this.jsd_3aMinIterate = jsd_3aMinIterate;
    }
    
    public java.lang.String getJsd_3aMinIterate() {
      return jsd_3aMinIterate;
    }
    
    @org.openjax.jsonx.StringProperty(name="jsd:maxIterate", pattern="([\\d]+|unbounded)", use=org.openjax.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String jsd_3aMaxIterate;
    
    public void setJsd_3aMaxIterate(final java.lang.String jsd_3aMaxIterate) {
      this.jsd_3aMaxIterate = jsd_3aMaxIterate;
    }
    
    public java.lang.String getJsd_3aMaxIterate() {
      return jsd_3aMaxIterate;
    }
    
    @org.openjax.jsonx.ObjectElement(id=6, type=org.openjax.jsonx.schema.ObjectElement.class, minOccurs=0, nullable=false)
    @org.openjax.jsonx.ObjectElement(id=5, type=org.openjax.jsonx.schema.StringElement.class, minOccurs=0, nullable=false)
    @org.openjax.jsonx.ObjectElement(id=4, type=org.openjax.jsonx.schema.ReferenceElement.class, minOccurs=0, nullable=false)
    @org.openjax.jsonx.ObjectElement(id=3, type=org.openjax.jsonx.schema.NumberElement.class, minOccurs=0, nullable=false)
    @org.openjax.jsonx.ObjectElement(id=2, type=org.openjax.jsonx.schema.BooleanElement.class, minOccurs=0, nullable=false)
    @org.openjax.jsonx.ObjectElement(id=1, type=org.openjax.jsonx.schema.ArrayElement.class, minOccurs=0, nullable=false)
    @org.openjax.jsonx.ObjectElement(id=0, type=org.openjax.jsonx.schema.AnyElement.class, minOccurs=0, nullable=false)
    @org.openjax.jsonx.ArrayProperty(name="jsd:elements", elementIds={0, 1, 2, 3, 4, 5, 6}, maxIterate=2147483647, nullable=false)
    private java.util.List<java.lang.Object> jsd_3aElements;
    
    public void setJsd_3aElements(final java.util.List<java.lang.Object> jsd_3aElements) {
      this.jsd_3aElements = jsd_3aElements;
    }
    
    public java.util.List<java.lang.Object> getJsd_3aElements() {
      return jsd_3aElements;
    }
    
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;
    
      if (!(obj instanceof org.openjax.jsonx.schema.Array) || !super.equals(obj))
        return false;
    
      final org.openjax.jsonx.schema.Array that = (org.openjax.jsonx.schema.Array)obj;
      if (that.jsd_3aClass != null ? !that.jsd_3aClass.equals(jsd_3aClass) : jsd_3aClass != null)
        return false;
    
      if (that.jsd_3aMinIterate != null ? !that.jsd_3aMinIterate.equals(jsd_3aMinIterate) : jsd_3aMinIterate != null)
        return false;
    
      if (that.jsd_3aMaxIterate != null ? !that.jsd_3aMaxIterate.equals(jsd_3aMaxIterate) : jsd_3aMaxIterate != null)
        return false;
    
      if (that.jsd_3aElements != null ? !that.jsd_3aElements.equals(jsd_3aElements) : jsd_3aElements != null)
        return false;
    
      return true;
    }
    
    @java.lang.Override
    public int hashCode() {
      int hashCode = -413333739 * 31 + super.hashCode();
      hashCode = 31 * hashCode + (jsd_3aClass == null ? 0 : jsd_3aClass.hashCode());
      hashCode = 31 * hashCode + (jsd_3aMinIterate == null ? 0 : jsd_3aMinIterate.hashCode());
      hashCode = 31 * hashCode + (jsd_3aMaxIterate == null ? 0 : jsd_3aMaxIterate.hashCode());
      hashCode = 31 * hashCode + (jsd_3aElements == null ? 0 : jsd_3aElements.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.JxEncoder.get().marshal(this);
    }
  }

  public static class ArrayElement extends org.openjax.jsonx.schema.Array {
    @org.openjax.jsonx.BooleanProperty(name="jsd:nullable", use=org.openjax.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.Boolean jsd_3aNullable;
    
    public void setJsd_3aNullable(final java.lang.Boolean jsd_3aNullable) {
      this.jsd_3aNullable = jsd_3aNullable;
    }
    
    public java.lang.Boolean getJsd_3aNullable() {
      return jsd_3aNullable;
    }
    
    @org.openjax.jsonx.StringProperty(name="jsd:minOccurs", pattern="[\\d]+", use=org.openjax.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String jsd_3aMinOccurs;
    
    public void setJsd_3aMinOccurs(final java.lang.String jsd_3aMinOccurs) {
      this.jsd_3aMinOccurs = jsd_3aMinOccurs;
    }
    
    public java.lang.String getJsd_3aMinOccurs() {
      return jsd_3aMinOccurs;
    }
    
    @org.openjax.jsonx.StringProperty(name="jsd:maxOccurs", pattern="([\\d]+|unbounded)", use=org.openjax.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String jsd_3aMaxOccurs;
    
    public void setJsd_3aMaxOccurs(final java.lang.String jsd_3aMaxOccurs) {
      this.jsd_3aMaxOccurs = jsd_3aMaxOccurs;
    }
    
    public java.lang.String getJsd_3aMaxOccurs() {
      return jsd_3aMaxOccurs;
    }
    
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;
    
      if (!(obj instanceof org.openjax.jsonx.schema.ArrayElement) || !super.equals(obj))
        return false;
    
      final org.openjax.jsonx.schema.ArrayElement that = (org.openjax.jsonx.schema.ArrayElement)obj;
      if (that.jsd_3aNullable != null ? !that.jsd_3aNullable.equals(jsd_3aNullable) : jsd_3aNullable != null)
        return false;
    
      if (that.jsd_3aMinOccurs != null ? !that.jsd_3aMinOccurs.equals(jsd_3aMinOccurs) : jsd_3aMinOccurs != null)
        return false;
    
      if (that.jsd_3aMaxOccurs != null ? !that.jsd_3aMaxOccurs.equals(jsd_3aMaxOccurs) : jsd_3aMaxOccurs != null)
        return false;
    
      return true;
    }
    
    @java.lang.Override
    public int hashCode() {
      int hashCode = 869367335 * 31 + super.hashCode();
      hashCode = 31 * hashCode + (jsd_3aNullable == null ? 0 : jsd_3aNullable.hashCode());
      hashCode = 31 * hashCode + (jsd_3aMinOccurs == null ? 0 : jsd_3aMinOccurs.hashCode());
      hashCode = 31 * hashCode + (jsd_3aMaxOccurs == null ? 0 : jsd_3aMaxOccurs.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.JxEncoder.get().marshal(this);
    }
  }

  public static class ArrayProperty extends org.openjax.jsonx.schema.Array {
    @org.openjax.jsonx.BooleanProperty(name="jsd:nullable", use=org.openjax.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.Boolean jsd_3aNullable;
    
    public void setJsd_3aNullable(final java.lang.Boolean jsd_3aNullable) {
      this.jsd_3aNullable = jsd_3aNullable;
    }
    
    public java.lang.Boolean getJsd_3aNullable() {
      return jsd_3aNullable;
    }
    
    @org.openjax.jsonx.StringProperty(name="jsd:use", pattern="(required|optional)", use=org.openjax.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String jsd_3aUse;
    
    public void setJsd_3aUse(final java.lang.String jsd_3aUse) {
      this.jsd_3aUse = jsd_3aUse;
    }
    
    public java.lang.String getJsd_3aUse() {
      return jsd_3aUse;
    }
    
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;
    
      if (!(obj instanceof org.openjax.jsonx.schema.ArrayProperty) || !super.equals(obj))
        return false;
    
      final org.openjax.jsonx.schema.ArrayProperty that = (org.openjax.jsonx.schema.ArrayProperty)obj;
      if (that.jsd_3aNullable != null ? !that.jsd_3aNullable.equals(jsd_3aNullable) : jsd_3aNullable != null)
        return false;
    
      if (that.jsd_3aUse != null ? !that.jsd_3aUse.equals(jsd_3aUse) : jsd_3aUse != null)
        return false;
    
      return true;
    }
    
    @java.lang.Override
    public int hashCode() {
      int hashCode = 195781642 * 31 + super.hashCode();
      hashCode = 31 * hashCode + (jsd_3aNullable == null ? 0 : jsd_3aNullable.hashCode());
      hashCode = 31 * hashCode + (jsd_3aUse == null ? 0 : jsd_3aUse.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.JxEncoder.get().marshal(this);
    }
  }

  public static class Boolean extends org.openjax.jsonx.schema.Member {
    @org.openjax.jsonx.StringProperty(name="jsd:class", pattern="boolean", nullable=false)
    private java.lang.String jsd_3aClass;
    
    public void setJsd_3aClass(final java.lang.String jsd_3aClass) {
      this.jsd_3aClass = jsd_3aClass;
    }
    
    public java.lang.String getJsd_3aClass() {
      return jsd_3aClass;
    }
    
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;
    
      if (!(obj instanceof org.openjax.jsonx.schema.Boolean) || !super.equals(obj))
        return false;
    
      final org.openjax.jsonx.schema.Boolean that = (org.openjax.jsonx.schema.Boolean)obj;
      if (that.jsd_3aClass != null ? !that.jsd_3aClass.equals(jsd_3aClass) : jsd_3aClass != null)
        return false;
    
      return true;
    }
    
    @java.lang.Override
    public int hashCode() {
      int hashCode = -1277574684 * 31 + super.hashCode();
      hashCode = 31 * hashCode + (jsd_3aClass == null ? 0 : jsd_3aClass.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.JxEncoder.get().marshal(this);
    }
  }

  public static class BooleanElement extends org.openjax.jsonx.schema.Boolean {
    @org.openjax.jsonx.BooleanProperty(name="jsd:nullable", use=org.openjax.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.Boolean jsd_3aNullable;
    
    public void setJsd_3aNullable(final java.lang.Boolean jsd_3aNullable) {
      this.jsd_3aNullable = jsd_3aNullable;
    }
    
    public java.lang.Boolean getJsd_3aNullable() {
      return jsd_3aNullable;
    }
    
    @org.openjax.jsonx.StringProperty(name="jsd:minOccurs", pattern="[\\d]+", use=org.openjax.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String jsd_3aMinOccurs;
    
    public void setJsd_3aMinOccurs(final java.lang.String jsd_3aMinOccurs) {
      this.jsd_3aMinOccurs = jsd_3aMinOccurs;
    }
    
    public java.lang.String getJsd_3aMinOccurs() {
      return jsd_3aMinOccurs;
    }
    
    @org.openjax.jsonx.StringProperty(name="jsd:maxOccurs", pattern="([\\d]+|unbounded)", use=org.openjax.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String jsd_3aMaxOccurs;
    
    public void setJsd_3aMaxOccurs(final java.lang.String jsd_3aMaxOccurs) {
      this.jsd_3aMaxOccurs = jsd_3aMaxOccurs;
    }
    
    public java.lang.String getJsd_3aMaxOccurs() {
      return jsd_3aMaxOccurs;
    }
    
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;
    
      if (!(obj instanceof org.openjax.jsonx.schema.BooleanElement) || !super.equals(obj))
        return false;
    
      final org.openjax.jsonx.schema.BooleanElement that = (org.openjax.jsonx.schema.BooleanElement)obj;
      if (that.jsd_3aNullable != null ? !that.jsd_3aNullable.equals(jsd_3aNullable) : jsd_3aNullable != null)
        return false;
    
      if (that.jsd_3aMinOccurs != null ? !that.jsd_3aMinOccurs.equals(jsd_3aMinOccurs) : jsd_3aMinOccurs != null)
        return false;
    
      if (that.jsd_3aMaxOccurs != null ? !that.jsd_3aMaxOccurs.equals(jsd_3aMaxOccurs) : jsd_3aMaxOccurs != null)
        return false;
    
      return true;
    }
    
    @java.lang.Override
    public int hashCode() {
      int hashCode = 1031982200 * 31 + super.hashCode();
      hashCode = 31 * hashCode + (jsd_3aNullable == null ? 0 : jsd_3aNullable.hashCode());
      hashCode = 31 * hashCode + (jsd_3aMinOccurs == null ? 0 : jsd_3aMinOccurs.hashCode());
      hashCode = 31 * hashCode + (jsd_3aMaxOccurs == null ? 0 : jsd_3aMaxOccurs.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.JxEncoder.get().marshal(this);
    }
  }

  public static class BooleanProperty extends org.openjax.jsonx.schema.Boolean {
    @org.openjax.jsonx.BooleanProperty(name="jsd:nullable", use=org.openjax.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.Boolean jsd_3aNullable;
    
    public void setJsd_3aNullable(final java.lang.Boolean jsd_3aNullable) {
      this.jsd_3aNullable = jsd_3aNullable;
    }
    
    public java.lang.Boolean getJsd_3aNullable() {
      return jsd_3aNullable;
    }
    
    @org.openjax.jsonx.StringProperty(name="jsd:use", pattern="(required|optional)", use=org.openjax.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String jsd_3aUse;
    
    public void setJsd_3aUse(final java.lang.String jsd_3aUse) {
      this.jsd_3aUse = jsd_3aUse;
    }
    
    public java.lang.String getJsd_3aUse() {
      return jsd_3aUse;
    }
    
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;
    
      if (!(obj instanceof org.openjax.jsonx.schema.BooleanProperty) || !super.equals(obj))
        return false;
    
      final org.openjax.jsonx.schema.BooleanProperty that = (org.openjax.jsonx.schema.BooleanProperty)obj;
      if (that.jsd_3aNullable != null ? !that.jsd_3aNullable.equals(jsd_3aNullable) : jsd_3aNullable != null)
        return false;
    
      if (that.jsd_3aUse != null ? !that.jsd_3aUse.equals(jsd_3aUse) : jsd_3aUse != null)
        return false;
    
      return true;
    }
    
    @java.lang.Override
    public int hashCode() {
      int hashCode = 941875161 * 31 + super.hashCode();
      hashCode = 31 * hashCode + (jsd_3aNullable == null ? 0 : jsd_3aNullable.hashCode());
      hashCode = 31 * hashCode + (jsd_3aUse == null ? 0 : jsd_3aUse.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.JxEncoder.get().marshal(this);
    }
  }

  public static abstract class Member implements org.openjax.jsonx.JxObject {
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
      return org.openjax.jsonx.JxEncoder.get().marshal(this);
    }
  }

  public static class Number extends org.openjax.jsonx.schema.Member {
    @org.openjax.jsonx.StringProperty(name="jsd:class", pattern="number", nullable=false)
    private java.lang.String jsd_3aClass;
    
    public void setJsd_3aClass(final java.lang.String jsd_3aClass) {
      this.jsd_3aClass = jsd_3aClass;
    }
    
    public java.lang.String getJsd_3aClass() {
      return jsd_3aClass;
    }
    
    @org.openjax.jsonx.StringProperty(name="jsd:form", pattern="(integer|real)", use=org.openjax.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String jsd_3aForm;
    
    public void setJsd_3aForm(final java.lang.String jsd_3aForm) {
      this.jsd_3aForm = jsd_3aForm;
    }
    
    public java.lang.String getJsd_3aForm() {
      return jsd_3aForm;
    }
    
    @org.openjax.jsonx.StringProperty(name="jsd:range", pattern="[\\(\\[](-?\\d+(\\.\\d+)?)?,(-?\\d+(\\.\\d+)?)?[\\)\\]]", use=org.openjax.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String jsd_3aRange;
    
    public void setJsd_3aRange(final java.lang.String jsd_3aRange) {
      this.jsd_3aRange = jsd_3aRange;
    }
    
    public java.lang.String getJsd_3aRange() {
      return jsd_3aRange;
    }
    
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;
    
      if (!(obj instanceof org.openjax.jsonx.schema.Number) || !super.equals(obj))
        return false;
    
      final org.openjax.jsonx.schema.Number that = (org.openjax.jsonx.schema.Number)obj;
      if (that.jsd_3aClass != null ? !that.jsd_3aClass.equals(jsd_3aClass) : jsd_3aClass != null)
        return false;
    
      if (that.jsd_3aForm != null ? !that.jsd_3aForm.equals(jsd_3aForm) : jsd_3aForm != null)
        return false;
    
      if (that.jsd_3aRange != null ? !that.jsd_3aRange.equals(jsd_3aRange) : jsd_3aRange != null)
        return false;
    
      return true;
    }
    
    @java.lang.Override
    public int hashCode() {
      int hashCode = 446357005 * 31 + super.hashCode();
      hashCode = 31 * hashCode + (jsd_3aClass == null ? 0 : jsd_3aClass.hashCode());
      hashCode = 31 * hashCode + (jsd_3aForm == null ? 0 : jsd_3aForm.hashCode());
      hashCode = 31 * hashCode + (jsd_3aRange == null ? 0 : jsd_3aRange.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.JxEncoder.get().marshal(this);
    }
  }

  public static class NumberElement extends org.openjax.jsonx.schema.Number {
    @org.openjax.jsonx.BooleanProperty(name="jsd:nullable", use=org.openjax.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.Boolean jsd_3aNullable;
    
    public void setJsd_3aNullable(final java.lang.Boolean jsd_3aNullable) {
      this.jsd_3aNullable = jsd_3aNullable;
    }
    
    public java.lang.Boolean getJsd_3aNullable() {
      return jsd_3aNullable;
    }
    
    @org.openjax.jsonx.StringProperty(name="jsd:minOccurs", pattern="[\\d]+", use=org.openjax.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String jsd_3aMinOccurs;
    
    public void setJsd_3aMinOccurs(final java.lang.String jsd_3aMinOccurs) {
      this.jsd_3aMinOccurs = jsd_3aMinOccurs;
    }
    
    public java.lang.String getJsd_3aMinOccurs() {
      return jsd_3aMinOccurs;
    }
    
    @org.openjax.jsonx.StringProperty(name="jsd:maxOccurs", pattern="([\\d]+|unbounded)", use=org.openjax.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String jsd_3aMaxOccurs;
    
    public void setJsd_3aMaxOccurs(final java.lang.String jsd_3aMaxOccurs) {
      this.jsd_3aMaxOccurs = jsd_3aMaxOccurs;
    }
    
    public java.lang.String getJsd_3aMaxOccurs() {
      return jsd_3aMaxOccurs;
    }
    
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;
    
      if (!(obj instanceof org.openjax.jsonx.schema.NumberElement) || !super.equals(obj))
        return false;
    
      final org.openjax.jsonx.schema.NumberElement that = (org.openjax.jsonx.schema.NumberElement)obj;
      if (that.jsd_3aNullable != null ? !that.jsd_3aNullable.equals(jsd_3aNullable) : jsd_3aNullable != null)
        return false;
    
      if (that.jsd_3aMinOccurs != null ? !that.jsd_3aMinOccurs.equals(jsd_3aMinOccurs) : jsd_3aMinOccurs != null)
        return false;
    
      if (that.jsd_3aMaxOccurs != null ? !that.jsd_3aMaxOccurs.equals(jsd_3aMaxOccurs) : jsd_3aMaxOccurs != null)
        return false;
    
      return true;
    }
    
    @java.lang.Override
    public int hashCode() {
      int hashCode = 798646319 * 31 + super.hashCode();
      hashCode = 31 * hashCode + (jsd_3aNullable == null ? 0 : jsd_3aNullable.hashCode());
      hashCode = 31 * hashCode + (jsd_3aMinOccurs == null ? 0 : jsd_3aMinOccurs.hashCode());
      hashCode = 31 * hashCode + (jsd_3aMaxOccurs == null ? 0 : jsd_3aMaxOccurs.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.JxEncoder.get().marshal(this);
    }
  }

  public static class NumberProperty extends org.openjax.jsonx.schema.Number {
    @org.openjax.jsonx.BooleanProperty(name="jsd:nullable", use=org.openjax.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.Boolean jsd_3aNullable;
    
    public void setJsd_3aNullable(final java.lang.Boolean jsd_3aNullable) {
      this.jsd_3aNullable = jsd_3aNullable;
    }
    
    public java.lang.Boolean getJsd_3aNullable() {
      return jsd_3aNullable;
    }
    
    @org.openjax.jsonx.StringProperty(name="jsd:use", pattern="(required|optional)", use=org.openjax.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String jsd_3aUse;
    
    public void setJsd_3aUse(final java.lang.String jsd_3aUse) {
      this.jsd_3aUse = jsd_3aUse;
    }
    
    public java.lang.String getJsd_3aUse() {
      return jsd_3aUse;
    }
    
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;
    
      if (!(obj instanceof org.openjax.jsonx.schema.NumberProperty) || !super.equals(obj))
        return false;
    
      final org.openjax.jsonx.schema.NumberProperty that = (org.openjax.jsonx.schema.NumberProperty)obj;
      if (that.jsd_3aNullable != null ? !that.jsd_3aNullable.equals(jsd_3aNullable) : jsd_3aNullable != null)
        return false;
    
      if (that.jsd_3aUse != null ? !that.jsd_3aUse.equals(jsd_3aUse) : jsd_3aUse != null)
        return false;
    
      return true;
    }
    
    @java.lang.Override
    public int hashCode() {
      int hashCode = -1996569854 * 31 + super.hashCode();
      hashCode = 31 * hashCode + (jsd_3aNullable == null ? 0 : jsd_3aNullable.hashCode());
      hashCode = 31 * hashCode + (jsd_3aUse == null ? 0 : jsd_3aUse.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.JxEncoder.get().marshal(this);
    }
  }

  public static abstract class Object extends org.openjax.jsonx.schema.Member {
    public static class Jsd_3aProperties implements org.openjax.jsonx.JxObject {
      @org.openjax.jsonx.AnyProperty(name=".*", types={@org.openjax.jsonx.t(objects=org.openjax.jsonx.schema.AnyProperty.class), @org.openjax.jsonx.t(objects=org.openjax.jsonx.schema.ArrayProperty.class), @org.openjax.jsonx.t(objects=org.openjax.jsonx.schema.BooleanProperty.class), @org.openjax.jsonx.t(objects=org.openjax.jsonx.schema.NumberProperty.class), @org.openjax.jsonx.t(objects=org.openjax.jsonx.schema.ObjectProperty.class), @org.openjax.jsonx.t(objects=org.openjax.jsonx.schema.ReferenceProperty.class), @org.openjax.jsonx.t(objects=org.openjax.jsonx.schema.StringProperty.class)}, nullable=false)
      public final java.util.LinkedHashMap<java.lang.String,java.lang.Object> _2e_2a = new java.util.LinkedHashMap<>();
      
      @java.lang.Override
      public boolean equals(final java.lang.Object obj) {
        if (obj == this)
          return true;
      
        if (!(obj instanceof org.openjax.jsonx.schema.Object.Jsd_3aProperties))
          return false;
      
        final org.openjax.jsonx.schema.Object.Jsd_3aProperties that = (org.openjax.jsonx.schema.Object.Jsd_3aProperties)obj;
        if (that._2e_2a != null ? !that._2e_2a.equals(_2e_2a) : _2e_2a != null)
          return false;
      
        return true;
      }
      
      @java.lang.Override
      public int hashCode() {
        int hashCode = 1356798086;
        hashCode = 31 * hashCode + (_2e_2a == null ? 0 : _2e_2a.hashCode());
        return hashCode;
      }
      
      @java.lang.Override
      public java.lang.String toString() {
        return org.openjax.jsonx.JxEncoder.get().marshal(this);
      }
    }
    @org.openjax.jsonx.StringProperty(name="jsd:class", pattern="object", nullable=false)
    private java.lang.String jsd_3aClass;
    
    public void setJsd_3aClass(final java.lang.String jsd_3aClass) {
      this.jsd_3aClass = jsd_3aClass;
    }
    
    public java.lang.String getJsd_3aClass() {
      return jsd_3aClass;
    }
    
    @org.openjax.jsonx.StringProperty(name="jsd:extends", use=org.openjax.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String jsd_3aExtends;
    
    public void setJsd_3aExtends(final java.lang.String jsd_3aExtends) {
      this.jsd_3aExtends = jsd_3aExtends;
    }
    
    public java.lang.String getJsd_3aExtends() {
      return jsd_3aExtends;
    }
    
    @org.openjax.jsonx.ObjectProperty(name="jsd:properties", use=org.openjax.jsonx.Use.OPTIONAL, nullable=false)
    private org.openjax.jsonx.schema.Object.Jsd_3aProperties jsd_3aProperties;
    
    public void setJsd_3aProperties(final org.openjax.jsonx.schema.Object.Jsd_3aProperties jsd_3aProperties) {
      this.jsd_3aProperties = jsd_3aProperties;
    }
    
    public org.openjax.jsonx.schema.Object.Jsd_3aProperties getJsd_3aProperties() {
      return jsd_3aProperties;
    }
    
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;
    
      if (!(obj instanceof org.openjax.jsonx.schema.Object) || !super.equals(obj))
        return false;
    
      final org.openjax.jsonx.schema.Object that = (org.openjax.jsonx.schema.Object)obj;
      if (that.jsd_3aClass != null ? !that.jsd_3aClass.equals(jsd_3aClass) : jsd_3aClass != null)
        return false;
    
      if (that.jsd_3aExtends != null ? !that.jsd_3aExtends.equals(jsd_3aExtends) : jsd_3aExtends != null)
        return false;
    
      if (that.jsd_3aProperties != null ? !that.jsd_3aProperties.equals(jsd_3aProperties) : jsd_3aProperties != null)
        return false;
    
      return true;
    }
    
    @java.lang.Override
    public int hashCode() {
      int hashCode = 457352707 * 31 + super.hashCode();
      hashCode = 31 * hashCode + (jsd_3aClass == null ? 0 : jsd_3aClass.hashCode());
      hashCode = 31 * hashCode + (jsd_3aExtends == null ? 0 : jsd_3aExtends.hashCode());
      hashCode = 31 * hashCode + (jsd_3aProperties == null ? 0 : jsd_3aProperties.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.JxEncoder.get().marshal(this);
    }
  }

  public static class ObjectElement extends org.openjax.jsonx.schema.Object {
    @org.openjax.jsonx.BooleanProperty(name="jsd:nullable", use=org.openjax.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.Boolean jsd_3aNullable;
    
    public void setJsd_3aNullable(final java.lang.Boolean jsd_3aNullable) {
      this.jsd_3aNullable = jsd_3aNullable;
    }
    
    public java.lang.Boolean getJsd_3aNullable() {
      return jsd_3aNullable;
    }
    
    @org.openjax.jsonx.StringProperty(name="jsd:minOccurs", pattern="[\\d]+", use=org.openjax.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String jsd_3aMinOccurs;
    
    public void setJsd_3aMinOccurs(final java.lang.String jsd_3aMinOccurs) {
      this.jsd_3aMinOccurs = jsd_3aMinOccurs;
    }
    
    public java.lang.String getJsd_3aMinOccurs() {
      return jsd_3aMinOccurs;
    }
    
    @org.openjax.jsonx.StringProperty(name="jsd:maxOccurs", pattern="([\\d]+|unbounded)", use=org.openjax.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String jsd_3aMaxOccurs;
    
    public void setJsd_3aMaxOccurs(final java.lang.String jsd_3aMaxOccurs) {
      this.jsd_3aMaxOccurs = jsd_3aMaxOccurs;
    }
    
    public java.lang.String getJsd_3aMaxOccurs() {
      return jsd_3aMaxOccurs;
    }
    
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;
    
      if (!(obj instanceof org.openjax.jsonx.schema.ObjectElement) || !super.equals(obj))
        return false;
    
      final org.openjax.jsonx.schema.ObjectElement that = (org.openjax.jsonx.schema.ObjectElement)obj;
      if (that.jsd_3aNullable != null ? !that.jsd_3aNullable.equals(jsd_3aNullable) : jsd_3aNullable != null)
        return false;
    
      if (that.jsd_3aMinOccurs != null ? !that.jsd_3aMinOccurs.equals(jsd_3aMinOccurs) : jsd_3aMinOccurs != null)
        return false;
    
      if (that.jsd_3aMaxOccurs != null ? !that.jsd_3aMaxOccurs.equals(jsd_3aMaxOccurs) : jsd_3aMaxOccurs != null)
        return false;
    
      return true;
    }
    
    @java.lang.Override
    public int hashCode() {
      int hashCode = 1364580217 * 31 + super.hashCode();
      hashCode = 31 * hashCode + (jsd_3aNullable == null ? 0 : jsd_3aNullable.hashCode());
      hashCode = 31 * hashCode + (jsd_3aMinOccurs == null ? 0 : jsd_3aMinOccurs.hashCode());
      hashCode = 31 * hashCode + (jsd_3aMaxOccurs == null ? 0 : jsd_3aMaxOccurs.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.JxEncoder.get().marshal(this);
    }
  }

  public static class ObjectProperty extends org.openjax.jsonx.schema.Object {
    @org.openjax.jsonx.BooleanProperty(name="jsd:nullable", use=org.openjax.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.Boolean jsd_3aNullable;
    
    public void setJsd_3aNullable(final java.lang.Boolean jsd_3aNullable) {
      this.jsd_3aNullable = jsd_3aNullable;
    }
    
    public java.lang.Boolean getJsd_3aNullable() {
      return jsd_3aNullable;
    }
    
    @org.openjax.jsonx.StringProperty(name="jsd:use", pattern="(required|optional)", use=org.openjax.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String jsd_3aUse;
    
    public void setJsd_3aUse(final java.lang.String jsd_3aUse) {
      this.jsd_3aUse = jsd_3aUse;
    }
    
    public java.lang.String getJsd_3aUse() {
      return jsd_3aUse;
    }
    
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;
    
      if (!(obj instanceof org.openjax.jsonx.schema.ObjectProperty) || !super.equals(obj))
        return false;
    
      final org.openjax.jsonx.schema.ObjectProperty that = (org.openjax.jsonx.schema.ObjectProperty)obj;
      if (that.jsd_3aNullable != null ? !that.jsd_3aNullable.equals(jsd_3aNullable) : jsd_3aNullable != null)
        return false;
    
      if (that.jsd_3aUse != null ? !that.jsd_3aUse.equals(jsd_3aUse) : jsd_3aUse != null)
        return false;
    
      return true;
    }
    
    @java.lang.Override
    public int hashCode() {
      int hashCode = -1632488200 * 31 + super.hashCode();
      hashCode = 31 * hashCode + (jsd_3aNullable == null ? 0 : jsd_3aNullable.hashCode());
      hashCode = 31 * hashCode + (jsd_3aUse == null ? 0 : jsd_3aUse.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.JxEncoder.get().marshal(this);
    }
  }

  public static class ObjectType extends org.openjax.jsonx.schema.Object {
    @org.openjax.jsonx.BooleanProperty(name="jsd:abstract", use=org.openjax.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.Boolean jsd_3aAbstract;
    
    public void setJsd_3aAbstract(final java.lang.Boolean jsd_3aAbstract) {
      this.jsd_3aAbstract = jsd_3aAbstract;
    }
    
    public java.lang.Boolean getJsd_3aAbstract() {
      return jsd_3aAbstract;
    }
    
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;
    
      if (!(obj instanceof org.openjax.jsonx.schema.ObjectType) || !super.equals(obj))
        return false;
    
      final org.openjax.jsonx.schema.ObjectType that = (org.openjax.jsonx.schema.ObjectType)obj;
      if (that.jsd_3aAbstract != null ? !that.jsd_3aAbstract.equals(jsd_3aAbstract) : jsd_3aAbstract != null)
        return false;
    
      return true;
    }
    
    @java.lang.Override
    public int hashCode() {
      int hashCode = -841879587 * 31 + super.hashCode();
      hashCode = 31 * hashCode + (jsd_3aAbstract == null ? 0 : jsd_3aAbstract.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.JxEncoder.get().marshal(this);
    }
  }

  public static abstract class Reference extends org.openjax.jsonx.schema.Member {
    @org.openjax.jsonx.StringProperty(name="jsd:class", pattern="reference", nullable=false)
    private java.lang.String jsd_3aClass;
    
    public void setJsd_3aClass(final java.lang.String jsd_3aClass) {
      this.jsd_3aClass = jsd_3aClass;
    }
    
    public java.lang.String getJsd_3aClass() {
      return jsd_3aClass;
    }
    
    @org.openjax.jsonx.StringProperty(name="jsd:type", nullable=false)
    private java.lang.String jsd_3aType;
    
    public void setJsd_3aType(final java.lang.String jsd_3aType) {
      this.jsd_3aType = jsd_3aType;
    }
    
    public java.lang.String getJsd_3aType() {
      return jsd_3aType;
    }
    
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;
    
      if (!(obj instanceof org.openjax.jsonx.schema.Reference) || !super.equals(obj))
        return false;
    
      final org.openjax.jsonx.schema.Reference that = (org.openjax.jsonx.schema.Reference)obj;
      if (that.jsd_3aClass != null ? !that.jsd_3aClass.equals(jsd_3aClass) : jsd_3aClass != null)
        return false;
    
      if (that.jsd_3aType != null ? !that.jsd_3aType.equals(jsd_3aType) : jsd_3aType != null)
        return false;
    
      return true;
    }
    
    @java.lang.Override
    public int hashCode() {
      int hashCode = 1922766343 * 31 + super.hashCode();
      hashCode = 31 * hashCode + (jsd_3aClass == null ? 0 : jsd_3aClass.hashCode());
      hashCode = 31 * hashCode + (jsd_3aType == null ? 0 : jsd_3aType.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.JxEncoder.get().marshal(this);
    }
  }

  public static class ReferenceElement extends org.openjax.jsonx.schema.Reference {
    @org.openjax.jsonx.BooleanProperty(name="jsd:nullable", use=org.openjax.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.Boolean jsd_3aNullable;
    
    public void setJsd_3aNullable(final java.lang.Boolean jsd_3aNullable) {
      this.jsd_3aNullable = jsd_3aNullable;
    }
    
    public java.lang.Boolean getJsd_3aNullable() {
      return jsd_3aNullable;
    }
    
    @org.openjax.jsonx.StringProperty(name="jsd:minOccurs", pattern="[\\d]+", use=org.openjax.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String jsd_3aMinOccurs;
    
    public void setJsd_3aMinOccurs(final java.lang.String jsd_3aMinOccurs) {
      this.jsd_3aMinOccurs = jsd_3aMinOccurs;
    }
    
    public java.lang.String getJsd_3aMinOccurs() {
      return jsd_3aMinOccurs;
    }
    
    @org.openjax.jsonx.StringProperty(name="jsd:maxOccurs", pattern="([\\d]+|unbounded)", use=org.openjax.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String jsd_3aMaxOccurs;
    
    public void setJsd_3aMaxOccurs(final java.lang.String jsd_3aMaxOccurs) {
      this.jsd_3aMaxOccurs = jsd_3aMaxOccurs;
    }
    
    public java.lang.String getJsd_3aMaxOccurs() {
      return jsd_3aMaxOccurs;
    }
    
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;
    
      if (!(obj instanceof org.openjax.jsonx.schema.ReferenceElement) || !super.equals(obj))
        return false;
    
      final org.openjax.jsonx.schema.ReferenceElement that = (org.openjax.jsonx.schema.ReferenceElement)obj;
      if (that.jsd_3aNullable != null ? !that.jsd_3aNullable.equals(jsd_3aNullable) : jsd_3aNullable != null)
        return false;
    
      if (that.jsd_3aMinOccurs != null ? !that.jsd_3aMinOccurs.equals(jsd_3aMinOccurs) : jsd_3aMinOccurs != null)
        return false;
    
      if (that.jsd_3aMaxOccurs != null ? !that.jsd_3aMaxOccurs.equals(jsd_3aMaxOccurs) : jsd_3aMaxOccurs != null)
        return false;
    
      return true;
    }
    
    @java.lang.Override
    public int hashCode() {
      int hashCode = -1440474379 * 31 + super.hashCode();
      hashCode = 31 * hashCode + (jsd_3aNullable == null ? 0 : jsd_3aNullable.hashCode());
      hashCode = 31 * hashCode + (jsd_3aMinOccurs == null ? 0 : jsd_3aMinOccurs.hashCode());
      hashCode = 31 * hashCode + (jsd_3aMaxOccurs == null ? 0 : jsd_3aMaxOccurs.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.JxEncoder.get().marshal(this);
    }
  }

  public static class ReferenceProperty extends org.openjax.jsonx.schema.Reference {
    @org.openjax.jsonx.BooleanProperty(name="jsd:nullable", use=org.openjax.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.Boolean jsd_3aNullable;
    
    public void setJsd_3aNullable(final java.lang.Boolean jsd_3aNullable) {
      this.jsd_3aNullable = jsd_3aNullable;
    }
    
    public java.lang.Boolean getJsd_3aNullable() {
      return jsd_3aNullable;
    }
    
    @org.openjax.jsonx.StringProperty(name="jsd:use", pattern="(required|optional)", use=org.openjax.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String jsd_3aUse;
    
    public void setJsd_3aUse(final java.lang.String jsd_3aUse) {
      this.jsd_3aUse = jsd_3aUse;
    }
    
    public java.lang.String getJsd_3aUse() {
      return jsd_3aUse;
    }
    
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;
    
      if (!(obj instanceof org.openjax.jsonx.schema.ReferenceProperty) || !super.equals(obj))
        return false;
    
      final org.openjax.jsonx.schema.ReferenceProperty that = (org.openjax.jsonx.schema.ReferenceProperty)obj;
      if (that.jsd_3aNullable != null ? !that.jsd_3aNullable.equals(jsd_3aNullable) : jsd_3aNullable != null)
        return false;
    
      if (that.jsd_3aUse != null ? !that.jsd_3aUse.equals(jsd_3aUse) : jsd_3aUse != null)
        return false;
    
      return true;
    }
    
    @java.lang.Override
    public int hashCode() {
      int hashCode = 1605132540 * 31 + super.hashCode();
      hashCode = 31 * hashCode + (jsd_3aNullable == null ? 0 : jsd_3aNullable.hashCode());
      hashCode = 31 * hashCode + (jsd_3aUse == null ? 0 : jsd_3aUse.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.JxEncoder.get().marshal(this);
    }
  }

  public static class Schema implements org.openjax.jsonx.JxObject {
    @org.openjax.jsonx.StringProperty(name="jsd:ns", pattern="http://jsonx.openjax.org/schema-0.2.2.jsd", nullable=false)
    private java.lang.String jsd_3aNs;
    
    public void setJsd_3aNs(final java.lang.String jsd_3aNs) {
      this.jsd_3aNs = jsd_3aNs;
    }
    
    public java.lang.String getJsd_3aNs() {
      return jsd_3aNs;
    }
    
    @org.openjax.jsonx.StringProperty(name="jsd:schemaLocation", pattern="http://jsonx.openjax.org/schema-0.2.2.jsd [^ ]+", use=org.openjax.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String jsd_3aSchemaLocation;
    
    public void setJsd_3aSchemaLocation(final java.lang.String jsd_3aSchemaLocation) {
      this.jsd_3aSchemaLocation = jsd_3aSchemaLocation;
    }
    
    public java.lang.String getJsd_3aSchemaLocation() {
      return jsd_3aSchemaLocation;
    }
    
    @org.openjax.jsonx.AnyProperty(name="[a-zA-Z_$][-a-zA-Z\\d_$]*", types={@org.openjax.jsonx.t(objects=org.openjax.jsonx.schema.Array.class), @org.openjax.jsonx.t(objects=org.openjax.jsonx.schema.Boolean.class), @org.openjax.jsonx.t(objects=org.openjax.jsonx.schema.Number.class), @org.openjax.jsonx.t(objects=org.openjax.jsonx.schema.ObjectType.class), @org.openjax.jsonx.t(objects=org.openjax.jsonx.schema.String.class)}, nullable=false)
    public final java.util.LinkedHashMap<java.lang.String,java.lang.Object> _5ba_2dZA_2dZ__$_5d_5b_2dA_2dZA_2dZ_5cD__$_5d_2a = new java.util.LinkedHashMap<>();
    
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;
    
      if (!(obj instanceof org.openjax.jsonx.schema.Schema))
        return false;
    
      final org.openjax.jsonx.schema.Schema that = (org.openjax.jsonx.schema.Schema)obj;
      if (that.jsd_3aNs != null ? !that.jsd_3aNs.equals(jsd_3aNs) : jsd_3aNs != null)
        return false;
    
      if (that.jsd_3aSchemaLocation != null ? !that.jsd_3aSchemaLocation.equals(jsd_3aSchemaLocation) : jsd_3aSchemaLocation != null)
        return false;
    
      if (that._5ba_2dZA_2dZ__$_5d_5b_2dA_2dZA_2dZ_5cD__$_5d_2a != null ? !that._5ba_2dZA_2dZ__$_5d_5b_2dA_2dZA_2dZ_5cD__$_5d_2a.equals(_5ba_2dZA_2dZ__$_5d_5b_2dA_2dZA_2dZ_5cD__$_5d_2a) : _5ba_2dZA_2dZ__$_5d_5b_2dA_2dZA_2dZ_5cD__$_5d_2a != null)
        return false;
    
      return true;
    }
    
    @java.lang.Override
    public int hashCode() {
      int hashCode = 572733541;
      hashCode = 31 * hashCode + (jsd_3aNs == null ? 0 : jsd_3aNs.hashCode());
      hashCode = 31 * hashCode + (jsd_3aSchemaLocation == null ? 0 : jsd_3aSchemaLocation.hashCode());
      hashCode = 31 * hashCode + (_5ba_2dZA_2dZ__$_5d_5b_2dA_2dZA_2dZ_5cD__$_5d_2a == null ? 0 : _5ba_2dZA_2dZ__$_5d_5b_2dA_2dZA_2dZ_5cD__$_5d_2a.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.JxEncoder.get().marshal(this);
    }
  }

  public static class String extends org.openjax.jsonx.schema.Member {
    @org.openjax.jsonx.StringProperty(name="jsd:class", pattern="string", nullable=false)
    private java.lang.String jsd_3aClass;
    
    public void setJsd_3aClass(final java.lang.String jsd_3aClass) {
      this.jsd_3aClass = jsd_3aClass;
    }
    
    public java.lang.String getJsd_3aClass() {
      return jsd_3aClass;
    }
    
    @org.openjax.jsonx.StringProperty(name="jsd:pattern", use=org.openjax.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String jsd_3aPattern;
    
    public void setJsd_3aPattern(final java.lang.String jsd_3aPattern) {
      this.jsd_3aPattern = jsd_3aPattern;
    }
    
    public java.lang.String getJsd_3aPattern() {
      return jsd_3aPattern;
    }
    
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;
    
      if (!(obj instanceof org.openjax.jsonx.schema.String) || !super.equals(obj))
        return false;
    
      final org.openjax.jsonx.schema.String that = (org.openjax.jsonx.schema.String)obj;
      if (that.jsd_3aClass != null ? !that.jsd_3aClass.equals(jsd_3aClass) : jsd_3aClass != null)
        return false;
    
      if (that.jsd_3aPattern != null ? !that.jsd_3aPattern.equals(jsd_3aPattern) : jsd_3aPattern != null)
        return false;
    
      return true;
    }
    
    @java.lang.Override
    public int hashCode() {
      int hashCode = 588735189 * 31 + super.hashCode();
      hashCode = 31 * hashCode + (jsd_3aClass == null ? 0 : jsd_3aClass.hashCode());
      hashCode = 31 * hashCode + (jsd_3aPattern == null ? 0 : jsd_3aPattern.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.JxEncoder.get().marshal(this);
    }
  }

  public static class StringElement extends org.openjax.jsonx.schema.String {
    @org.openjax.jsonx.BooleanProperty(name="jsd:nullable", use=org.openjax.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.Boolean jsd_3aNullable;
    
    public void setJsd_3aNullable(final java.lang.Boolean jsd_3aNullable) {
      this.jsd_3aNullable = jsd_3aNullable;
    }
    
    public java.lang.Boolean getJsd_3aNullable() {
      return jsd_3aNullable;
    }
    
    @org.openjax.jsonx.StringProperty(name="jsd:minOccurs", pattern="[\\d]+", use=org.openjax.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String jsd_3aMinOccurs;
    
    public void setJsd_3aMinOccurs(final java.lang.String jsd_3aMinOccurs) {
      this.jsd_3aMinOccurs = jsd_3aMinOccurs;
    }
    
    public java.lang.String getJsd_3aMinOccurs() {
      return jsd_3aMinOccurs;
    }
    
    @org.openjax.jsonx.StringProperty(name="jsd:maxOccurs", pattern="([\\d]+|unbounded)", use=org.openjax.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String jsd_3aMaxOccurs;
    
    public void setJsd_3aMaxOccurs(final java.lang.String jsd_3aMaxOccurs) {
      this.jsd_3aMaxOccurs = jsd_3aMaxOccurs;
    }
    
    public java.lang.String getJsd_3aMaxOccurs() {
      return jsd_3aMaxOccurs;
    }
    
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;
    
      if (!(obj instanceof org.openjax.jsonx.schema.StringElement) || !super.equals(obj))
        return false;
    
      final org.openjax.jsonx.schema.StringElement that = (org.openjax.jsonx.schema.StringElement)obj;
      if (that.jsd_3aNullable != null ? !that.jsd_3aNullable.equals(jsd_3aNullable) : jsd_3aNullable != null)
        return false;
    
      if (that.jsd_3aMinOccurs != null ? !that.jsd_3aMinOccurs.equals(jsd_3aMinOccurs) : jsd_3aMinOccurs != null)
        return false;
    
      if (that.jsd_3aMaxOccurs != null ? !that.jsd_3aMaxOccurs.equals(jsd_3aMaxOccurs) : jsd_3aMaxOccurs != null)
        return false;
    
      return true;
    }
    
    @java.lang.Override
    public int hashCode() {
      int hashCode = 648833639 * 31 + super.hashCode();
      hashCode = 31 * hashCode + (jsd_3aNullable == null ? 0 : jsd_3aNullable.hashCode());
      hashCode = 31 * hashCode + (jsd_3aMinOccurs == null ? 0 : jsd_3aMinOccurs.hashCode());
      hashCode = 31 * hashCode + (jsd_3aMaxOccurs == null ? 0 : jsd_3aMaxOccurs.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.JxEncoder.get().marshal(this);
    }
  }

  public static class StringProperty extends org.openjax.jsonx.schema.String {
    @org.openjax.jsonx.BooleanProperty(name="jsd:nullable", use=org.openjax.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.Boolean jsd_3aNullable;
    
    public void setJsd_3aNullable(final java.lang.Boolean jsd_3aNullable) {
      this.jsd_3aNullable = jsd_3aNullable;
    }
    
    public java.lang.Boolean getJsd_3aNullable() {
      return jsd_3aNullable;
    }
    
    @org.openjax.jsonx.StringProperty(name="jsd:use", pattern="(required|optional)", use=org.openjax.jsonx.Use.OPTIONAL, nullable=false)
    private java.lang.String jsd_3aUse;
    
    public void setJsd_3aUse(final java.lang.String jsd_3aUse) {
      this.jsd_3aUse = jsd_3aUse;
    }
    
    public java.lang.String getJsd_3aUse() {
      return jsd_3aUse;
    }
    
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;
    
      if (!(obj instanceof org.openjax.jsonx.schema.StringProperty) || !super.equals(obj))
        return false;
    
      final org.openjax.jsonx.schema.StringProperty that = (org.openjax.jsonx.schema.StringProperty)obj;
      if (that.jsd_3aNullable != null ? !that.jsd_3aNullable.equals(jsd_3aNullable) : jsd_3aNullable != null)
        return false;
    
      if (that.jsd_3aUse != null ? !that.jsd_3aUse.equals(jsd_3aUse) : jsd_3aUse != null)
        return false;
    
      return true;
    }
    
    @java.lang.Override
    public int hashCode() {
      int hashCode = 1949171658 * 31 + super.hashCode();
      hashCode = 31 * hashCode + (jsd_3aNullable == null ? 0 : jsd_3aNullable.hashCode());
      hashCode = 31 * hashCode + (jsd_3aUse == null ? 0 : jsd_3aUse.hashCode());
      return hashCode;
    }
    
    @java.lang.Override
    public java.lang.String toString() {
      return org.openjax.jsonx.JxEncoder.get().marshal(this);
    }
  }
}