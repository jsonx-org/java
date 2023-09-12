package org.jsonx;

@java.lang.SuppressWarnings("all")
@javax.annotation.Generated(value="org.jsonx.Generator", date="2023-09-12T12:16:24.632")
public class include {
  @org.jsonx.JxBinding(targetNamespace="http://www.jsonx.org/include-0.4.jsdx")
  public static class Include implements org.jsonx.JxObject {
    @org.jsonx.StringProperty(name="@ns", pattern="http://www.jsonx.org/include-0.4.jsd", nullable=false)
    public java.lang.String get40ns() {
      return _40ns;
    }

    public Include set40ns(final java.lang.String _40ns) {
      this._40ns = _40ns;
      return this;
    }

    private java.lang.String _40ns;

    @org.jsonx.StringProperty(name="href", nullable=false)
    public java.lang.String getHref() {
      return href;
    }

    public Include setHref(final java.lang.String href) {
      this.href = href;
      return this;
    }

    private java.lang.String href;

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof org.jsonx.include.Include))
        return false;

      final org.jsonx.include.Include that = (org.jsonx.include.Include)obj;
      if (!org.libj.lang.ObjectUtil.equals(_40ns, that._40ns))
        return false;

      if (!org.libj.lang.ObjectUtil.equals(href, that.href))
        return false;

      return true;
    }

    @java.lang.Override
    public int hashCode() {
      int hashCode = -1630008412;
      if (_40ns != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(_40ns);

      if (href != null)
        hashCode = 31 * hashCode + org.libj.lang.ObjectUtil.hashCode(href);

      return hashCode;
    }

    @java.lang.Override
    public java.lang.String toString() {
      return org.jsonx.JxEncoder.get().toString(this);
    }
  }
}