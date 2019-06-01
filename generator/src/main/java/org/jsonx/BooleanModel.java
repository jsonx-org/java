/* Copyright (c) 2017 JSONx
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

package org.jsonx;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.jsonx.www.schema_0_2_3.xL0gluGCXYYJc;
import org.libj.lang.IllegalAnnotationException;

final class BooleanModel extends Model {
  private static final Id ID = Id.hashed("b");

  private static xL0gluGCXYYJc.Schema.Boolean type(final String name) {
    final xL0gluGCXYYJc.Schema.Boolean xsb = new xL0gluGCXYYJc.Schema.Boolean();
    if (name != null)
      xsb.setName$(new xL0gluGCXYYJc.Schema.Boolean.Name$(name));

    return xsb;
  }

  private static xL0gluGCXYYJc.$Boolean property(final schema.BooleanProperty jsd, final String name) {
    final xL0gluGCXYYJc.$Boolean xsb = new xL0gluGCXYYJc.$Boolean() {
      private static final long serialVersionUID = 650722913732574568L;

      @Override
      protected xL0gluGCXYYJc.$Member inherits() {
        return new xL0gluGCXYYJc.$ObjectMember.Property();
      }
    };

    if (name != null)
      xsb.setName$(new xL0gluGCXYYJc.$Boolean.Name$(name));

    if (jsd.getJsd_3aNullable() != null)
      xsb.setNullable$(new xL0gluGCXYYJc.$Boolean.Nullable$(jsd.getJsd_3aNullable()));

    if (jsd.getJsd_3aUse() != null)
      xsb.setUse$(new xL0gluGCXYYJc.$Boolean.Use$(xL0gluGCXYYJc.$Boolean.Use$.Enum.valueOf(jsd.getJsd_3aUse())));

    return xsb;
  }

  private static xL0gluGCXYYJc.$ArrayMember.Boolean element(final schema.BooleanElement jsd) {
    final xL0gluGCXYYJc.$ArrayMember.Boolean xsb = new xL0gluGCXYYJc.$ArrayMember.Boolean();
    if (jsd.getJsd_3aNullable() != null)
      xsb.setNullable$(new xL0gluGCXYYJc.$ArrayMember.Boolean.Nullable$(jsd.getJsd_3aNullable()));

    if (jsd.getJsd_3aMinOccurs() != null)
      xsb.setMinOccurs$(new xL0gluGCXYYJc.$ArrayMember.Boolean.MinOccurs$(Integer.parseInt(jsd.getJsd_3aMinOccurs())));

    if (jsd.getJsd_3aMaxOccurs() != null)
      xsb.setMaxOccurs$(new xL0gluGCXYYJc.$ArrayMember.Boolean.MaxOccurs$(jsd.getJsd_3aMaxOccurs()));

    return xsb;
  }

  static xL0gluGCXYYJc.$BooleanMember jsdToXsb(final schema.Boolean jsd, final String name) {
    final xL0gluGCXYYJc.$BooleanMember xsb;
    if (jsd instanceof schema.BooleanProperty)
      xsb = property((schema.BooleanProperty)jsd, name);
    else if (jsd instanceof schema.BooleanElement)
      xsb = element((schema.BooleanElement)jsd);
    else if (name != null)
      xsb = type(name);
    else
      throw new UnsupportedOperationException("Unsupported type: " + jsd.getClass().getName());

    if (jsd.getJsd_3aDoc() != null && jsd.getJsd_3aDoc().length() > 0)
      xsb.setDoc$(new xL0gluGCXYYJc.$Documented.Doc$(jsd.getJsd_3aDoc()));

    return xsb;
  }

  static BooleanModel declare(final Registry registry, final Declarer declarer, final xL0gluGCXYYJc.Schema.Boolean binding) {
    return registry.declare(binding).value(new BooleanModel(registry, declarer, binding), null);
  }

  static Reference referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final BooleanProperty property, final Field field) {
    final BooleanModel model = new BooleanModel(registry, referrer, property, field);
    final Id id = model.id;

    final BooleanModel registered = (BooleanModel)registry.getModel(id);
    return new Reference(registry, referrer, JsdUtil.getName(property.name(), field), property.nullable(), property.use(), registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
  }

  static Reference referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final BooleanElement element) {
    final BooleanModel model = new BooleanModel(registry, referrer, element);
    final Id id = model.id;

    final BooleanModel registered = (BooleanModel)registry.getModel(id);
    return new Reference(registry, referrer, element.nullable(), element.minOccurs(), element.maxOccurs(), registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
  }

  static BooleanModel reference(final Registry registry, final Referrer<?> referrer, final xL0gluGCXYYJc.$Array.Boolean binding) {
    return registry.reference(new BooleanModel(registry, referrer, binding), referrer);
  }

  static BooleanModel reference(final Registry registry, final Referrer<?> referrer, final xL0gluGCXYYJc.$Boolean binding) {
    return registry.reference(new BooleanModel(registry, referrer, binding), referrer);
  }

  private BooleanModel(final Registry registry, final Declarer declarer, final xL0gluGCXYYJc.Schema.Boolean binding) {
    super(registry, declarer, Id.named(binding.getName$()), binding.getDoc$());
  }

  private BooleanModel(final Registry registry, final Declarer declarer, final xL0gluGCXYYJc.$Boolean binding) {
    super(registry, declarer, ID, binding.getDoc$(), binding.getName$(), binding.getNullable$(), binding.getUse$());
  }

  private BooleanModel(final Registry registry, final Declarer declarer, final xL0gluGCXYYJc.$Array.Boolean binding) {
    super(registry, declarer, ID, binding.getDoc$(), binding.getNullable$(), binding.getMinOccurs$(), binding.getMaxOccurs$());
  }

  private BooleanModel(final Registry registry, final Declarer declarer, final BooleanProperty property, final Field field) {
    super(registry, declarer, ID, property.nullable(), property.use());
    if (!isAssignable(field, Boolean.class, false, property.nullable(), property.use()) || field.getType() == boolean.class && (property.use() == Use.OPTIONAL || property.nullable()))
      throw new IllegalAnnotationException(property, field.getDeclaringClass().getName() + "." + field.getName() + ": @" + BooleanProperty.class.getSimpleName() + " can only be applied to fields of String type with use=\"required\" or nullable=false, or of primitive boolean type with use=\"required\" and nullable=false, or of Optional<Boolean> type with use=\"optional\" and nullable=true");
  }

  private BooleanModel(final Registry registry, final Declarer declarer, final BooleanElement element) {
    super(registry, declarer, ID, element.nullable(), null);
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