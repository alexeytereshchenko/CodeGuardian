package io.github.Anth3995.guardian.extention;

import java.util.HashSet;
import java.util.Set;

public class ErrorProneExtension {
  private boolean enable = true;
  private String dependency = "com.google.errorprone:error_prone_core:2.23.0";
  private String dependencyJavac = "com.google.errorprone:javac:9+181-r4173-1";

  private Set<String> bugPatterns = new HashSet<>();

  public ErrorProneExtension() {
    bugPatterns.add("WildcardImport");
    bugPatterns.add("RemoveUnusedImports");
    bugPatterns.add("FieldCanBeFinal");
    bugPatterns.add("ConstantField");
    bugPatterns.add("UnusedMethod");
    bugPatterns.add("UnusedVariable");
    bugPatterns.add("ReturnsNullCollection");
    bugPatterns.add("UnnecessaryBoxedVariable");
    bugPatterns.add("UnnecessaryBoxedAssignment");
    bugPatterns.add("SwitchDefault");
    bugPatterns.add("PrivateConstructorForUtilityClass");
    bugPatterns.add("UseEnumSwitch");
    bugPatterns.add("MultipleTopLevelClasses");
    bugPatterns.add("MissingBraces");
    bugPatterns.add("UnnecessaryStaticImport");
    bugPatterns.add("BadImport");
    bugPatterns.add("MixedArrayDimensions");
    bugPatterns.add("FieldCanBeLocal");

    bugPatterns.add("AlreadyChecked");
    bugPatterns.add("AmbiguousMethodReference");
    bugPatterns.add("ArgumentSelectionDefectChecker");
    bugPatterns.add("AttemptedNegativeZero");
    bugPatterns.add("BadComparable");
    bugPatterns.add("BadInstanceof");
    bugPatterns.add("BareDotMetacharacter");
    bugPatterns.add("BigDecimalEquals");
    bugPatterns.add("BigDecimalLiteralDouble");
    bugPatterns.add("BoxedPrimitiveConstructor");
    bugPatterns.add("ByteBufferBackingArray");
    bugPatterns.add("CatchAndPrintStackTrace");
    bugPatterns.add("CatchFail");
    bugPatterns.add("CharacterGetNumericValue");
    bugPatterns.add("ClassNewInstance");
    bugPatterns.add("CloseableProvides");
    bugPatterns.add("ClosingStandardOutputStreams");
    bugPatterns.add("CollectionUndefinedEquality");
    bugPatterns.add("ComplexBooleanConstant");
    bugPatterns.add("CompareToZero");
    bugPatterns.add("ComparableAndComparator");
    bugPatterns.add("CollectorShouldNotUseState");
    bugPatterns.add("DateChecker");
    bugPatterns.add("DateFormatConstant");
    bugPatterns.add("DeprecatedVariable");
    bugPatterns.add("DistinctVarargsChecker");
    bugPatterns.add("DoNotCallSuggester");
    bugPatterns.add("DoNotClaimAnnotations");
    bugPatterns.add("DoubleCheckedLocking");
    bugPatterns.add("DuplicateDateFormatField");
    bugPatterns.add("EmptyCatch");
    bugPatterns.add("EmptyTopLevelDeclaration");
    bugPatterns.add("EqualsIncompatibleType");
    bugPatterns.add("EqualsUnsafeCast");
    bugPatterns.add("EqualsUsingHashCode");
    bugPatterns.add("ExtendsObject");
    bugPatterns.add("FallThrough");
    bugPatterns.add("Finalize");
    bugPatterns.add("Finally");
    bugPatterns.add("FloatCast");
    bugPatterns.add("FloatingPointLiteralPrecision");
    bugPatterns.add("FloggerArgumentToString");
    bugPatterns.add("GetClassOnEnum");
    bugPatterns.add("HidingField");
    bugPatterns.add("IdentityHashMapUsage");
    bugPatterns.add("InconsistentCapitalization");
    bugPatterns.add("InconsistentHashCode");
    bugPatterns.add("IncorrectMainMethod");
    bugPatterns.add("IncrementInForLoopAndHeader");
    bugPatterns.add("InjectInvalidTargetingOnScopingAnnotation");
    bugPatterns.add("IntLongMath");
    bugPatterns.add("InvalidThrows");
    bugPatterns.add("IterableAndIterator");
    bugPatterns.add("JavaDurationGetSecondsGetNano");
    bugPatterns.add("JavaDurationWithNanos");
    bugPatterns.add("JavaDurationWithSeconds");
    bugPatterns.add("JavaInstantGetSecondsGetNano");
    bugPatterns.add("JavaLangClash");
    bugPatterns.add("JavaUtilDate");
    bugPatterns.add("JavaxInjectOnFinalField");
    bugPatterns.add("JdkObsolete");
    bugPatterns.add("LogicalAssignment");
    bugPatterns.add("LoopOverCharArray");
    bugPatterns.add("LongDoubleConversion");
    bugPatterns.add("LongFloatConversion");
    bugPatterns.add("MalformedInlineTag");
    bugPatterns.add("MathAbsoluteNegative");
    bugPatterns.add("MissingCasesInEnumSwitch");
    bugPatterns.add("MissingImplementsComparable");
    bugPatterns.add("MissingOverride");
    bugPatterns.add("MixedMutabilityReturnType");
    bugPatterns.add("ModifyCollectionInEnhancedForLoop");
    bugPatterns.add("ModifySourceCollectionInStream");
    bugPatterns.add("NarrowCalculation");
    bugPatterns.add("NarrowingCompoundAssignment");
    bugPatterns.add("NestedInstanceOfConditions");
    bugPatterns.add("NonCanonicalType");
    bugPatterns.add("NullOptional");
    bugPatterns.add("NullableConstructor");
    bugPatterns.add("NullablePrimitive");
    bugPatterns.add("NullablePrimitiveArray");
    bugPatterns.add("NullableVoid");
    bugPatterns.add("ObjectEqualsForPrimitives");
    bugPatterns.add("ObjectToString");
    bugPatterns.add("ObjectsHashCodePrimitive");
    bugPatterns.add("OptionalMapToOptional");
    bugPatterns.add("OptionalNotPresent");
    bugPatterns.add("OrphanedFormatString");
    bugPatterns.add("OverrideThrowableToString");
    bugPatterns.add("Overrides");
    bugPatterns.add("OverridingMethodInconsistentArgumentNamesChecker");
    bugPatterns.add("QualifierOrScopeOnInjectMethod");
    bugPatterns.add("ReturnAtTheEndOfVoidFunction");
    bugPatterns.add("StreamResourceLeak");
    bugPatterns.add("StreamToIterable");
    bugPatterns.add("StringSplitter");
    bugPatterns.add("SuperEqualsIsObjectEquals");
    bugPatterns.add("ThreadLocalUsage");
    bugPatterns.add("ToStringReturnsNull");
    bugPatterns.add("UndefinedEquals");
    bugPatterns.add("UnicodeEscape");
    bugPatterns.add("UnnecessaryLongToIntConversion");
    bugPatterns.add("UnnecessaryStringBuilder");
    bugPatterns.add("UnusedNestedClass");
    bugPatterns.add("UnusedTypeParameter");
  }

  public boolean isEnable() {
    return enable;
  }

  public void setEnable(boolean enable) {
    this.enable = enable;
  }

  public Set<String> getBugPatterns() {
    return bugPatterns;
  }

  public void setBugPatterns(Set<String> bugPatterns) {
    this.bugPatterns = bugPatterns;
  }

  public String getDependency() {
    return dependency;
  }

  public void setDependency(String dependency) {
    this.dependency = dependency;
  }

  public String getDependencyJavac() {
    return dependencyJavac;
  }

  public void setDependencyJavac(String dependencyJavac) {
    this.dependencyJavac = dependencyJavac;
  }
}
