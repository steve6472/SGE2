package steve6472.sge.main.game;

import java.util.Objects;
import java.util.regex.Pattern;

/**********************
 * Created by steve6472 (Mirek Jozefek)
 * On date: 7/17/2021
 * Project: VoxWorld
 *
 ***********************/
public record Id(String namespace, String id)
{
	private static final Pattern ID_MATCH = Pattern.compile("[a-z0-9_]*");

	public Id
	{
		if (namespace == null)
			throw new IllegalArgumentException("Namespace is null!");
		if (id == null)
			throw new IllegalArgumentException("Id is null!");
		if (!ID_MATCH.matcher(namespace).matches())
			throw new IllegalArgumentException("Namespace does not match pattern [a-z0-9_]* '" + namespace + "'");
		if (!ID_MATCH.matcher(id).matches())
			throw new IllegalArgumentException("Id does not match pattern [a-z0-9_]* '" + id + "'");
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Id id1 = (Id) o;
		return namespace.equals(id1.namespace) && id.equals(id1.id);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(namespace, id);
	}

	/**
	 *
	 * @param other Id to test against
	 * @return true if namespaces match
	 */
	public boolean compareNamespace(Id other)
	{
		return namespace.equals(other.namespace);
	}

	@Override
	public String toString()
	{
		return namespace + ":" + id;
	}
}
