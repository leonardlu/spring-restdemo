package restdemo.utils;

import org.assertj.core.api.AbstractAssert;
import static org.assertj.core.api.Assertions.assertThat;
import restdemo.objects.RestObject;

public class AssertUtils extends AbstractAssert<AssertUtils, RestObject> {

	public AssertUtils(RestObject actual) {
		super(actual, AssertUtils.class);
	}

	public static AssertUtils assertThatRestObject( RestObject actual) {
		return new AssertUtils(actual);
	}

	public AssertUtils hasId(String expectedId) {
		isNotNull();

		String actualId = actual.getId();
		assertThat(actualId)
		.overridingErrorMessage("Expected id to be <%s> but was <%s>",
				expectedId,
				actualId
				)
				.isEqualTo(expectedId);

		return this;
	}

	public AssertUtils hasContents(String expectedContents) {
		isNotNull();

		String actualContents = actual.getContents();
		assertThat(actualContents)
		.overridingErrorMessage("Expected contents to be <%s> but was <%s>",
				expectedContents,
				actualContents
				)
				.isEqualTo(expectedContents);

		return this;
	}

	public AssertUtils hasNoContents() {
		isNotNull();

		String actualContents = actual.getContents();
		assertThat(actualContents)
		.overridingErrorMessage("Expected contents to be <null> but was <%s>", actualContents)
		.isNull();

		return this;
	}

	public AssertUtils hasNoId() {
		isNotNull();

		String actualId = actual.getId();
		assertThat(actualId)
		.overridingErrorMessage("Expected id to be <null> but was <%s>", actualId)
		.isNull();

		return this;
	}

}
