package org.zero.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class ListWrapper<E> implements IListWrapper<E> {

	private List<E> instance;
	private List<IListListener<E>> listenerList = new ArrayList<IListListener<E>>();
	private IListListener<E> notifier = new  Notifier<E>(listenerList);

	public ListWrapper(List<E> instance) {
		this.instance = instance;
	}

	@Override
	public void addListener(IListListener<E> listener) {
		listenerList.add(listener);
	}

	@Override
	public void removeListener(IListListener<E> listener) {
		listenerList.remove(listener);
	}

	private static class Notifier<E> implements IListListener<E> {

		private List<IListListener<E>> listeners;

		public Notifier(List<IListListener<E>> listeners) {
			this.listeners = listeners;
		}

		@Override
		public void add(int index, E element) {
			for (IListListener<E> listener : listeners) {
				listener.add(index, element);
			}
		}

		@Override
		public void set(int index, E element) {
			for (IListListener<E> listener : listeners) {
				listener.set(index, element);
			}
		}

		@Override
		public void remove(int index, E element) {
			for (IListListener<E> listener : listeners) {
				listener.remove(index, element);
			}
		}

	}

	// Início do wrapper
	@Override
	public int size() {
		return instance.size();
	}

	@Override
	public boolean isEmpty() {
		return instance.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return instance.contains(o);
	}

	@Override
	public Iterator<E> iterator() {
		return instance.iterator();
	}

	@Override
	public Object[] toArray() {
		return instance.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return instance.toArray(a);
	}

	@Override
	public boolean add(E e) {
		boolean added = instance.add(e);
		if (added) {
			notifier.add(instance.size() - 1, e);
		}
		return added;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean remove(Object o) {
		int indexOfRemoved = instance.indexOf(o);
		boolean removed = instance.remove(o);
		if (removed) {
			// Se conseguiu remover, é por que o instanceof E
			notifier.remove(indexOfRemoved, (E) o);
		}
		return removed;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return instance.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		int sizeBeforeAdd = instance.size();
		boolean added = instance.addAll(c);

		if (added) {
			Iterator<? extends E> iterator = c.iterator();
			E element;
			for (int i = 0; (element = iterator.next()) != null; i++) {
				notifier.add(sizeBeforeAdd + i, element);
			}
		}

		return added;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		boolean added = instance.addAll(index, c);

		if (added) {
			Iterator<? extends E> iterator = c.iterator();
			E element;
			for (int i = 0; (element = iterator.next()) != null; i++) {
				notifier.add(index + i, element);
			}
		}

		return added;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean removeAll(Collection<?> c) {
		int[] indexes = new int[c.size()];

		Iterator<?> iterator = c.iterator();
		Object element;
		for (int i = 0; (element = iterator.next()) != null; i++) {
			indexes[i] = instance.indexOf(element);
		}

		boolean removed = instance.removeAll(c);

		if (removed) {
			iterator = c.iterator();

			for (int i = 0; (element = iterator.next()) != null; i++) {
				notifier.remove(indexes[i], (E) element);
			}
		}

		return removed;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO: por enquanto não vou suportar essa operação
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void clear() {
		Object[] oldList = instance.toArray(new Object[0]);

		instance.clear();

		for (int i = 0; i < oldList.length; i++) {
			notifier.remove(i, (E) oldList[i]);
		}
	}

	@Override
	public E get(int index) {
		return instance.get(index);
	}

	@Override
	public E set(int index, E element) {
		E e = instance.set(index, element);
		
		notifier.set(index, element);
		
		return e;
	}

	@Override
	public void add(int index, E element) {
		instance.add(index, element);
		notifier.add(index, element);
	}

	@Override
	public E remove(int index) {
		E element = instance.remove(index);
		if (element != null) {
			notifier.remove(index, element);
		}
		
		return element;
	}

	@Override
	public int indexOf(Object o) {
		return instance.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return instance.lastIndexOf(o);
	}

	@Override
	public ListIterator<E> listIterator() {
		return instance.listIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return instance.listIterator(index);
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		return instance.subList(fromIndex, toIndex);
	}

}
