/**
 *
 * $Id$
 */
package net.opengis.ows10.validation;

import net.opengis.ows10.CodeType;
import org.eclipse.emf.common.util.EList;

/**
 * A sample validator interface for {@link net.opengis.ows10.KeywordsType}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface KeywordsTypeValidator {
  boolean validate();

  boolean validateKeyword(EList<String> value);

  boolean validateType(EList<CodeType> value);

  boolean validateKeyword(String value);
  boolean validateType(CodeType value);
}
