import { Link, useNavigate } from 'react-router-dom';
import { Button, Form, Input, message, DatePicker, Select } from 'antd';

import axios from 'axios';
import { setToken } from '../../controle/cookie';

function CadastroPaciente() {

  const navigate = useNavigate(); 
  

  const onFinish = async (values: { nome: string, nomeSocial: string, cpf: string, dataNascimento: string, prefix: string,telefone: string, sexo: string, email: string, senha: string }) => {
    try {
        const telefoneCompleto = `${values.prefix}${values.telefone}`;
      const response = await axios.post(
        'http://localhost:8081/auth/register/paciente',
        {
            nomePaciente: values.nome,
            nomeSocial: values.nomeSocial,
            telefonePaciente: telefoneCompleto, // Aqui já vem o telefone completo com prefixo
            cpfPaciente: values.cpf,
            dataNascimento: values.dataNascimento,
            sexoPaciente: values.sexo,
            email: values.email,
            senha: values.senha,
            userrole: "PACIENTE"
        },
        {
          headers: {
            'Content-Type': 'application/json',
          },

          withCredentials: true 
        }
      );
        setToken(response.data.token);
        message.success('Paciente cadastrado com sucesso!');
      navigate('/');
    } catch (error) {
        message.success('Paciente cadastrado com sucesso!');
    }
  };

  const { Option } = Select;

  const prefixSelector = (
    <Form.Item name="prefix" noStyle>
      <Select style={{ width: 70 }}>
        <Option value="71">+71</Option>
        <Option value="73">+73</Option>
        <Option value="74">+74</Option>
        <Option value="75">+75</Option>
        <Option value="77">+77</Option>
        <Option value="79">+79</Option>
        <Option value="81">+81</Option>
        <Option value="82">+82</Option>
        <Option value="83">+83</Option>
        <Option value="84">+84</Option>
        <Option value="85">+85</Option>
        <Option value="86">+86</Option>
        <Option value="87">+87</Option>
        <Option value="88">+88</Option>
        <Option value="89">+89</Option>
        <Option value="91">+91</Option>
        <Option value="92">+92</Option>
        <Option value="93">+93</Option>
        <Option value="94">+94</Option>
        <Option value="95">+95</Option>
        <Option value="98">+98</Option>
        <Option value="99">+99</Option>
      </Select>
    </Form.Item>
  );

  return (
    <div>
      <h2 style={{ color: '#262626' }}>Cadastro de Pacientes</h2>
      <Form
        name="layout-multiple-vertical"
        layout="vertical"
        labelCol={{ span: 4 }}
        wrapperCol={{ span: 20 }}
        onFinish={onFinish}
      >
        <Form.Item label="Nome" name="nome" rules={[{ required: true }]}>
          <Input />
        </Form.Item>
        <Form.Item label="Nome Social" name="nomeSocial" rules={[{ required: false }]}>
          <Input />
        </Form.Item>
        <Form.Item
          name="telefone"
          label="Telefone"
          rules={[{ required: true, message: 'Por favor, informe seu telefone' }]}
        >
          <Input addonBefore={prefixSelector} style={{ width: '100%' }} />
        </Form.Item>
        <Form.Item label="CPF" name="cpf" rules={[{ required: true }]}>
          <Input />
        </Form.Item>
        <Form.Item name="dataNascimento" label="Data nascimento">
          <DatePicker />
        </Form.Item>
        <Form.Item label="Sexo" name="sexo" rules={[{ required: true }]}>
          <Input />
        </Form.Item>
        <Form.Item label="Email" name="email" rules={[{ required: true }]}>
          <Input />
        </Form.Item>
        <Form.Item label="Senha" name="senha" rules={[{ required: true }]}>
          <Input />
        </Form.Item>
        <Form.Item
                label="Confirmar senha"
                name="password2"
                dependencies={['senha']}
                rules={[
                {
                    required: true,
                },
                ({ getFieldValue }) => ({
                    validator(_, value) {
                    if (!value || getFieldValue('senha') === value) {
                        return Promise.resolve();
                    }
                    return Promise.reject(new Error('Senhas incompatíveis '));
                    },
                }),
                ]}
            >
                <Input />
            </Form.Item>
        <Button type="dashed" htmlType="submit">Cadastrar-se</Button>
      </Form>
      <Link to="/">Já possui uma conta? Faça login</Link>
    </div>
  );
}

export default CadastroPaciente;
