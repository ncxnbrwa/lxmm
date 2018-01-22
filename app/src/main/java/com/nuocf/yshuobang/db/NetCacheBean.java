/**
 * 
 */
package com.nuocf.yshuobang.db;


import org.xutils.db.annotation.Column;

/**
 * @author luquan
 *
 * 2014-7-24 上午11:29:31
 */
public class NetCacheBean extends EntityBase{
	
	@Column(name = "key")
	public String key;

	@Column(name = "content")
	public String content;
}
