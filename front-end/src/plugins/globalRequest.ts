/**
 * request 网络请求工具
 * 更详细的 api 文档: https://github.com/umijs/umi-request
 */
import { extend } from 'umi-request';
import { message, notification } from 'antd';
import { history } from 'umi';

/**
 * 配置request请求时的默认参数
 */
const request = extend({
  credentials: 'include', // 默认请求是否带上cookie
  // requestType: 'form',
});

/**
 * 所以请求拦截器
 */
request.interceptors.request.use((url, options): any => {
  console.log('do request url =>', url);
  return {
    url,
    options: {
      ...options,
      headers: {},
    },
  };
});

/**
 * 所有响应拦截器
 */
request.interceptors.response.use(async (response, options): Promise<any> => {
  const res = await response.clone().json();
  console.log(res);
  if (res.code === 0) {
    return res.data;
  } else {
    message.error(res.desc);
    return null;
  }
  return res.data;
});

export default request;
