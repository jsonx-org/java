/* Copyright (c) 2017 OpenJAX
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * You should have received a copy of The MIT License (MIT) along with this
 * program. If not, see <http://opensource.org/licenses/MIT/>.
 */

package org.openjax.jsonx.generator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.openjax.jsonx.schema;
import org.openjax.jsonx.runtime.BooleanElement;
import org.openjax.jsonx.runtime.BooleanProperty;
import org.openjax.jsonx.runtime.JxUtil;
import org.openjax.jsonx.runtime.Use;
import org.openjax.jsonx.schema_0_9_8.xL4gluGCXYYJc;

final class BooleanModel extends Model {
  private static final Id ID = Id.hashed("b");

  private static xL4gluGCXYYJc.Schema.BooleanType type(final String name) {
    final xL4gluGCXYYJc.Schema.BooleanType xsb = new xL4gluGCXYYJc.Schema.BooleanType();
    if (name != null)
      xsb.setName$(new xL4gluGCXYYJc.Schema.BooleanType.Name$(name));

    return xsb;
  }

  private static xL4gluGCXYYJc.$Boolean property(final schema.BooleanProperty jsonx, final String name) {
    final xL4gluGCXYYJc.$Boolean xsb = new xL4gluGCXYYJc.$Boolean() {
      private static final long serialVersionUID = 650722913732574568L;

      @Override
      protected xL4gluGCXYYJc.$Member inherits() {
        return new xL4gluGCXYYJc.$ObjectMember.Property();
      }
    };

    if (name != null)
      xsb.setName$(new xL4gluGCXYYJc.$Boolean.Name$(name));

    if (jsonx.getNullable() != null)
      xsb.setNullable$(new xL4gluGCXYYJc.$Boolean.Nullable$(jsonx.getNullable()));

    if (jsonx.getUse() != null)
      xsb.setUse$(new xL4gluGCXYYJc.$Boolean.Use$(xL4gluGCXYYJc.$Boolean.Use$.Enum.valueOf(jsonx.getUse())));

    return xsb;
  }

  private static xL4gluGCXYYJc.$ArrayMember.Boolean element(final schema.BooleanElement jsonx) {
    final xL4gluGCXYYJc.$ArrayMember.Boolean xsb = new xL4gluGCXYYJc.$ArrayMember.Boolean();
    if (jsonx.getNullable() != null)
      xsb.setNullable$(new xL4gluGCXYYJc.$ArrayMember.Boolean.Nullable$(jsonx.getNullable()));

    if (jsonx.getMinOccurs() != null)
      xsb.setMinOccurs$(new xL4gluGCXYYJc.$ArrayMember.Boolean.MinOccurs$(Integer.parseInt(jsonx.getMinOccurs())));

    if (jsonx.getMaxOccurs() != null)
      xsb.setMaxOccurs$(new xL4gluGCXYYJc.$ArrayMember.Boolean.MaxOccurs$(jsonx.getMaxOccurs()));

    return xsb;
  }

  static xL4gluGCXYYJc.$BooleanMember jsonxToXsb(final schema.Boolean jsonx, final String name) {
    final xL4gluGCXYYJc.$BooleanMember xsb;
    if (jsonx instanceof schema.BooleanType)
      xsb = type(name);
    else if (jsonx instanceof schema.BooleanProperty)
      xsb = property((schema.BooleanProperty)jsonx, name);
    else if (jsonx instanceof schema.BooleanElement)
      xsb = element((schema.BooleanElement)jsonx);
    else
      throw new UnsupportedOperationException("Unsupported type: " + jsonx.getClass().getName());

    return xsb;
  }

  static BooleanModel declare(final Registry registry, final xL4gluGCXYYJc.Schema.BooleanType binding) {
    return registry.declare(binding).value(new BooleanModel(registry, binding), null);
  }

  static Reference referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final BooleanProperty property, final Field field) {
    final BooleanModel model = new BooleanModel(registry, property, field);
    final Id id = model.id;

    final BooleanModel registered = (BooleanModel)registry.getModel(id);
    return new Reference(registry, JxUtil.getName(property.name(), field), property.nullable(), property.use(), registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
  }

  static Reference referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final BooleanElement element) {
    final BooleanModel model = new BooleanModel(registry, element);
    final Id id = model.id;

    final BooleanModel registered = (BooleanModel)registry.getModel(id);
    return new Reference(registry, element.nullable(), element.minOccurs(), element.maxOccurs(), registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
  }

  static BooleanModel reference(final Registry registry, final Referrer<?> referrer, final xL4gluGCXYYJc.$Array.Boolean binding) {
    return registry.reference(new BooleanModel(registry, binding), referrer);
  }

  static BooleanModel reference(final Registry registry, final Referrer<?> referrer, final xL4gluGCXYYJc.$Boolean binding) {
    return registry.reference(new BooleanModel(registry, binding), referrer);
  }

  private BooleanModel(final Registry registry, final xL4gluGCXYYJc.Schema.BooleanType binding) {
    super(registry, Id.named(binding.getName$()));
  }

  private BooleanModel(final Registry registry, final xL4gluGCXYYJc.$Boolean binding) {
    super(registry, ID, binding.getName$(), binding.getNullable$(), binding.getUse$());
  }

  private BooleanModel(final Registry registry, final xL4gluGCXYYJc.$Array.Boolean binding) {
    super(registry, ID, binding.getNullable$(), binding.getMinOccurs$(), binding.getMaxOccurs$());
  }

  private BooleanModel(final Registry registry, final BooleanProperty property, final Field field) {
    super(registry, ID, property.nullable(), property.use());
    if (!isAssignable(field, Boolean.class, false, property.nullable(), property.use()) && (field.getType() != boolean.class || property.use() == Use.OPTIONAL))
      isAssignable(field, Boolean.class, false, property.nullable(), property.use());
//      throw new IllegalAnnotationException(property, field.getDeclaringClass().getName() + "." + field.getName() + ": @" + BooleanProperty.class.getSimpleName() + " can only be applied to required or not-nullable fields of Boolean type, non-nullable boolean type, or optional and nullable fields of Optional<Boolean> type");
  }

  private BooleanModel(final Registry registry, final BooleanElement element) {
    super(registry, ID, element.nullable(), null);
  }

  @Override
  Registry.Type type() {
    return registry.getType(Boolean.class);
  }

  @Override
  String elementName() {
    return "boolean";
  }

  @Override
  Class<? extends Annotation> propertyAnnotation() {
    return BooleanProperty.class;
  }

  @Override
  Class<? extends Annotation> elementAnnotation() {
    return BooleanElement.class;
  }
}