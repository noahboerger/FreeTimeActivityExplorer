/**
 *
 */
package org.dhbw.mosbach.ai.pizzafactory.articleservice.testhelper;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.archive.importer.MavenImporter;

/**
 * @author Alexander.Auch
 *
 */
public final class TestHelper
{
	/**
	 * Creates a war containing the complete build artifact.
	 *
	 * @return complete build artifact.
	 */
	public static WebArchive createWholeBuild()
	{
		return ShrinkWrap.create(MavenImporter.class).loadPomFromFile("pom.xml").importBuildOutput().as(WebArchive.class);
	}
}
