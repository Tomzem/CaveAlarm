package com.tomzem.cavealarm.application;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.List;

/**
 * author:Tomze
 * date:2019/2/26 19:42
 * description:
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

    protected List<T> mDatas;
    protected Reference<Context> mContextRef;
    private int resID;

    public CommonAdapter(List<T> list, Context context, int resID) {
        this.mDatas = list;
        mContextRef = new WeakReference<Context>(context);
        this.resID = resID;
    }

    /**
     * 替换元素并刷新
     *
     * @param datas
     */
    public void refresh(List<T> datas) {
        mDatas = datas;
        this.notifyDataSetChanged();
    }

    /**
     * 删除元素并更新
     *
     * @param position
     */
    public void deleteList(int position) {
        mDatas.remove(position);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonViewHolder holder = CommonViewHolder.getHolder(mContextRef.get(), convertView, resID);
        fillData(position, holder);
        return holder.getContentView();
    }

    public abstract void fillData(int position, CommonViewHolder holder);
    public  void onDataChange(List<T> datas){
        mDatas = datas;
        notifyDataSetChanged();
    }

}