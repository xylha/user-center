import { CODE_NAV } from '@/constants';
import { GithubOutlined } from '@ant-design/icons';
import { DefaultFooter } from '@ant-design/pro-components';
const Footer: React.FC = () => {
  const defaultMessage = '解云玲修改出品';
  const currentYear = new Date().getFullYear();
  return (
    <DefaultFooter
      copyright={`${currentYear} ${defaultMessage}`}
      links={[
        {
          key: 'Ant Design Pro',
          title: '解老板第一个全栈项目',
          href: 'https://pro.ant.design',
          blankTarget: true,
        },
        {
          key: 'Ant Design',
          title: '编程导航',
          href: CODE_NAV,
          blankTarget: true,
        },
        {
          key: 'github',
          title: <><GithubOutlined /> 鱼皮的github</>,
          href: 'https://github.com/yupi',
          blankTarget: true,
        },
        
      ]}
    />
  );
};
export default Footer;
