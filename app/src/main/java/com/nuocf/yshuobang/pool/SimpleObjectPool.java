package com.nuocf.yshuobang.pool;

import java.util.LinkedList;

public class SimpleObjectPool<O> implements ObjectPool<O> {

	public SimpleObjectPool(int size) {
		mMaxPoolSize = size;
	}

	@Override
	public O acquire() {
		synchronized (this) {
			if (mLinkedList != null) {
				return mLinkedList.poll();
			}
		}
		return null;
	}

	@Override
	public void release(O object) {
		synchronized (this) {
			if (0 == mMaxPoolSize) {
				mLinkedList.add(object);
			} else if (mLinkedList.size() >= mMaxPoolSize) {
				return;
			} else if (!mLinkedList.contains(object)) {
				mLinkedList.add(object);
			}
		}
	}

	@Override
	public void setMaxPoolSize(int size) {
		synchronized (this) {
			if (size > 0 && size < mMaxPoolSize) {
				int len = mMaxPoolSize - size;
				for (int i = 0; i < len; i++) {
					mLinkedList.poll();
				}
			}
			mMaxPoolSize = size;
		}
	}

	@Override
	public int getMaxPoolSize() {
		return mMaxPoolSize;
	}

	private LinkedList<O> mLinkedList = new LinkedList<O>();
	private int mMaxPoolSize;
}
