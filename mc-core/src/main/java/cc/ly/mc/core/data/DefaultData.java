package cc.ly.mc.core.data;

import cc.ly.mc.core.data.impl.Unsigned32;

/**
 * 抽象数据类型基类
 * 
 * @author ly
 * 
 * @param <T>
 */
public abstract class DefaultData<T> implements Data<T> {

	protected T data;

	protected Unsigned32 length;

	public DefaultData(T data) {
		this.data = data;
	}

	public Unsigned32 length() {
		return length;
	}
	
	public void length(Unsigned32 length) {
		this.length = length;
	}

	public T get() {
		return data;
	}

	public void set(T data) {
		this.data = data;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DefaultData<?> other = (DefaultData<?>) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		return true;
	}

}
