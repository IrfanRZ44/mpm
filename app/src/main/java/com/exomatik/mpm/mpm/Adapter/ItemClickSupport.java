package com.exomatik.mpm.mpm.Adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnChildAttachStateChangeListener;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;

public class ItemClickSupport
{
  private OnChildAttachStateChangeListener mAttachListener = new OnChildAttachStateChangeListener()
  {
    public void onChildViewAttachedToWindow(View paramAnonymousView)
    {
      if (ItemClickSupport.this.mOnItemClickListener != null) {
        paramAnonymousView.setOnClickListener(ItemClickSupport.this.mOnClickListener);
      }
      if (ItemClickSupport.this.mOnItemLongClickListener != null) {
        paramAnonymousView.setOnLongClickListener(ItemClickSupport.this.mOnLongClickListener);
      }
    }
    
    public void onChildViewDetachedFromWindow(View paramAnonymousView) {}
  };
  private OnClickListener mOnClickListener = new OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      if (ItemClickSupport.this.mOnItemClickListener != null)
      {
        ViewHolder localViewHolder = ItemClickSupport.this.mRecyclerView.getChildViewHolder(paramAnonymousView);
        ItemClickSupport.this.mOnItemClickListener.onItemClicked(ItemClickSupport.this.mRecyclerView, localViewHolder.getAdapterPosition(), paramAnonymousView);
      }
    }
  };
  private OnItemClickListener mOnItemClickListener;
  private OnItemLongClickListener mOnItemLongClickListener;
  private OnLongClickListener mOnLongClickListener = new OnLongClickListener()
  {
    public boolean onLongClick(View paramAnonymousView)
    {
      if (ItemClickSupport.this.mOnItemLongClickListener != null)
      {
        ViewHolder localViewHolder = ItemClickSupport.this.mRecyclerView.getChildViewHolder(paramAnonymousView);
        return ItemClickSupport.this.mOnItemLongClickListener.onItemLongClicked(ItemClickSupport.this.mRecyclerView, localViewHolder.getAdapterPosition(), paramAnonymousView);
      }
      return false;
    }
  };
  private final RecyclerView mRecyclerView;
  
  private ItemClickSupport(RecyclerView paramRecyclerView)
  {
    this.mRecyclerView = paramRecyclerView;
    this.mRecyclerView.setTag(2131230857, this);
    this.mRecyclerView.addOnChildAttachStateChangeListener(this.mAttachListener);
  }
  
  public static ItemClickSupport addTo(RecyclerView paramRecyclerView)
  {
    ItemClickSupport localItemClickSupport = (ItemClickSupport)paramRecyclerView.getTag(2131230857);
    if (localItemClickSupport == null) {
      localItemClickSupport = new ItemClickSupport(paramRecyclerView);
    }
    return localItemClickSupport;
  }
  
  private void detach(RecyclerView paramRecyclerView)
  {
    paramRecyclerView.removeOnChildAttachStateChangeListener(this.mAttachListener);
    paramRecyclerView.setTag(2131230857, null);
  }
  
  public static ItemClickSupport removeFrom(RecyclerView paramRecyclerView)
  {
    ItemClickSupport localItemClickSupport = (ItemClickSupport)paramRecyclerView.getTag(2131230857);
    if (localItemClickSupport != null) {
      localItemClickSupport.detach(paramRecyclerView);
    }
    return localItemClickSupport;
  }
  
  public ItemClickSupport setOnItemClickListener(OnItemClickListener paramOnItemClickListener)
  {
    this.mOnItemClickListener = paramOnItemClickListener;
    return this;
  }
  
  public ItemClickSupport setOnItemLongClickListener(OnItemLongClickListener paramOnItemLongClickListener)
  {
    this.mOnItemLongClickListener = paramOnItemLongClickListener;
    return this;
  }
  
  public static abstract interface OnItemClickListener
  {
    public abstract void onItemClicked(RecyclerView paramRecyclerView, int paramInt, View paramView);
  }
  
  public static abstract interface OnItemLongClickListener
  {
    public abstract boolean onItemLongClicked(RecyclerView paramRecyclerView, int paramInt, View paramView);
  }
}
