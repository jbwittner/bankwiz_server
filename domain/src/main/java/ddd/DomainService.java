package ddd;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * The DomainService annotation is used to indicate that an annotated class is a domain service within a DDD (Domain-Driven Design) architecture.
 * <p>
 * A domain service is a class that plays a role in the domain model but doesn't naturally belong to an entity or value object.
 * This annotation should be used to make the role of such classes explicit.
 * </p>
 *
 * <b>Example:</b>
 * <pre>
 * {@code
 * @DomainService
 * public class SomeDomainService {
 *     // Some code here
 * }
 * </pre>
 *
 * @see <a href="https://domaindrivendesign.org/">Domain-Driven Design</a>
 * 
 * @since 1.0
 * @author Your Name
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface DomainService {}
