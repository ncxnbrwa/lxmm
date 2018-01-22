package com.nuocf.yshuobang.utils;

public class PageControl
{
    //起始页
    public static int START_PAGE_INDEX = 1;
    //每页数据条数
    public static int ONE_PAGE_SIZE = 10;
    private int page = START_PAGE_INDEX;

    private int totalpage = 0;

    private int pageSize = ONE_PAGE_SIZE;

    private int selectPosition = 0;

    public PageControl()
    {

    }

    // 设置页码
    public void setPage(int index)
    {
        this.page = index;
    }

    // 获取当前页码
    public int getPage()
    {
        return this.page;
    }

    // 增加一页
    public void plusPage()
    {
        this.page++;
    }

    // 减少一页
    public void reducePage()
    {
        if (this.page > START_PAGE_INDEX)
        {
            this.page--;
        }
    }

    // 重置页码
    public void resetPage()
    {
        this.page = START_PAGE_INDEX;
    }

    // 设置总页码
    public void setTotalPage(int count)
    {
        this.totalpage = count;
    }

    // 获取总页码
    public int getTotalPage()
    {
        return this.totalpage;
    }

    // 重置总页码
    public void resetTotalPage()
    {
        this.totalpage = 0;
    }

    public void setSelectPosition(int position)
    {
        this.selectPosition = position;
    }

    public int getSelectPosition()
    {
        return this.selectPosition;
    }

    public void resetSelectPosition()
    {
        this.selectPosition = 0;
    }

    public int getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

    public boolean hasNextPage()
    {
        return page < totalpage;
    }

}
