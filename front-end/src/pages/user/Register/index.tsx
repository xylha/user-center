import Footer from '@/components/Footer';
import { register } from '@/services/ant-design-pro/api';
import { LockOutlined, UserOutlined } from '@ant-design/icons';
import { LoginForm, ProFormText } from '@ant-design/pro-components';
import { message, Tabs } from 'antd';
import React, { useState } from 'react';
import { history } from 'umi';
import styles from './index.less';
import { CODE_NAV, SYSTEM_LOGIN } from '@/constants';

const Register: React.FC = () => {
  const [type, setType] = useState<string>('account');

  const handleSubmit = async (values: API.RegisterParams) => {
    const { userAccount, userPassword, checkPassword } = values;
    // 校验
    if (userAccount.length < 4) {
      message.error('账户名不得小于4位');
      return;
    }
    if (userPassword.length < 8 || checkPassword.length < 8) {
      message.error('密码不得小于8位');
      return;
    }
    if (userPassword !== checkPassword) {
      message.error('两次密码输入不一致');
      return;
    }
    try {
      // 注册
      const id = await register(values);
      console.log('id', id);
      if (id) {
        const defaultLoginSuccessMessage = '注册成功！';
        message.success(defaultLoginSuccessMessage);

        /** 此方法会跳转到 redirect 参数所在的位置 */
        /* 如果在登录之前，在某个页面，登录之后再跳转回去 */
        if (!history) return;
        const { query } = history.location;
        /* const { redirect } = query as {
          redirect: string;
        }; */
        history.push({
          pathname: '/user/login',
          query,
        });
        return;
      }
    } catch (error) {
      console.log('eee', error);
      const defaultLoginFailureMessage = '注册失败，请重试！';
      message.error(defaultLoginFailureMessage);
    }
  };
  return (
    <div className={styles.container}>
      <div className={styles.content}>
        <LoginForm
          logo={<img alt="logo" src={SYSTEM_LOGIN} />}
          submitter={{
            searchConfig: {
              submitText: '注册',
            },
          }}
          title="解老板"
          subTitle={
            <a href={CODE_NAV} target="blank">
              跟着鱼皮编程导航做项目
            </a>
          }
          initialValues={{
            autoLogin: true,
          }}
          onFinish={async (values) => {
            await handleSubmit(values as API.RegisterParams);
          }}
        >
          <Tabs activeKey={type} onChange={setType}>
            <Tabs.TabPane key="account" tab={'账户密码注册'} />
          </Tabs>

          {type === 'account' && (
            <>
              <ProFormText
                name="userAccount"
                fieldProps={{
                  size: 'large',
                  prefix: <UserOutlined className={styles.prefixIcon} />,
                }}
                placeholder={'请输入账户'}
                rules={[
                  {
                    required: true,
                    message: '账户是必填项！',
                  },
                ]}
              />
              <ProFormText.Password
                name="userPassword"
                fieldProps={{
                  size: 'large',
                  prefix: <LockOutlined className={styles.prefixIcon} />,
                }}
                placeholder={'请输入密码'}
                rules={[
                  {
                    required: true,
                    message: '密码是必填项！',
                  },
                  {
                    min: 8,
                    message: '密码最小为8位',
                  },
                ]}
              />
              <ProFormText.Password
                name="checkPassword"
                fieldProps={{
                  size: 'large',
                  prefix: <LockOutlined className={styles.prefixIcon} />,
                }}
                placeholder={'请确认密码'}
                rules={[
                  {
                    required: true,
                    message: '确定密码是必填项！',
                  },
                  {
                    min: 8,
                    message: '确定密码最小为8位',
                  },
                ]}
              />
              <ProFormText.Password
                name="plantCode"
                fieldProps={{
                  size: 'large',
                  prefix: <LockOutlined className={styles.prefixIcon} />,
                }}
                placeholder={'请输入星球编号'}
                rules={[
                  {
                    required: true,
                    message: '星球编号是必填项！',
                  },
                ]}
              />
            </>
          )}
        </LoginForm>
      </div>
      <Footer />
    </div>
  );
};
export default Register;
