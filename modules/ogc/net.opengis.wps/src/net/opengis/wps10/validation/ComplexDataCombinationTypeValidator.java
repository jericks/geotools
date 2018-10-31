/**
 *
 * $Id$
 */
package net.opengis.wps10.validation;

import net.opengis.wps10.ComplexDataDescriptionType;

/**
 * A sample validator interface for {@link net.opengis.wps10.ComplexDataCombinationType}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface ComplexDataCombinationTypeValidator {
  boolean validate();

  boolean validateFormat(ComplexDataDescriptionType value);
}
