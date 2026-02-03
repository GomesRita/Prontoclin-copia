import { useState} from 'react';
import { Button, Form, Input, message   } from 'antd';
import { getToken } from '../../controle/cookie';
import axios from 'axios';

function CadastroAdmin(){
    const [ loading ,setLoading] = useState(false); // Gerenciar o carregamento durante a requisição
    const [ error ,setError] = useState<string | null>(null); // Gerenciar erro de requisição

    // Função chamada ao enviar o formulário
    const onFinish = async (values: { nome: string, cpf: string, email: string, senha: string }) => {
        setLoading(true); // Inicia o carregamento
        setError(null); // Limpa o erro

        try {
            const token = getToken(); // Recupera o token do cookie
            if (token) {
                const response = await axios.post(
                    'http://localhost:8081/auth/register/adm', // Endereço da API para cadastrar administrador
                    {
                        nome: values.nome,
                        cpf: values.cpf,
                        email: values.email,
                        senha: values.senha,
                        userrole: "ADMIN"
                    },
                    {
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': `Bearer ${token}`, // Adicionando token no cabeçalho
                        }
                    }

                );
                console.log(response.data)
                // Se a requisição for bem-sucedida
                message.success('Administrador cadastrado com sucesso!'); // Exibe mensagem de sucesso
            } else {
                setError('Token não encontrado');
            }
        } catch (err) {
            setError('Erro ao cadastrar administrador');
            message.error('Erro ao cadastrar administrador'); // Exibe mensagem de erro
        } finally {
            setLoading(false); // Finaliza o carregamento
        }
    };

    if (loading) {
        return <div>Carregando...</div>;
    }
    
    
    if (error) {
        return <div>Erro ao carregar os dados: {error}</div>;
    }


    return (
        <div style={{display:'flex' ,justifyContent: 'center', alignItems: 'center'}}>
            <Form
                name="layout-multiple-vertical"
                layout="vertical"
                labelCol={{ span: 100 }}
                wrapperCol={{ span: 100 }}
                onFinish={onFinish}
                style={{width: '50%'}}
                >
                <h2 style={{ color: '#262626' }}>Cadastro de Administradores</h2>
                <Form.Item 
                    label="Nome" 
                    name="nome"
                    rules={[{ required: true, message: 'Por favor, insira um nome!' }]}>
                <Input />
                </Form.Item>
                <Form.Item 
                    label="CPF"
                    name="cpf"
                    rules={[{ required: true, message: 'Por favor, insira um CPF!' }]}>
                <Input />
                </Form.Item>
                <Form.Item 
                    label="Email"
                    name="email"
                    rules={[{ required: true, message: 'Por favor, insira um email!' }]}>
                <Input />
                </Form.Item>
                <Form.Item 
                    label="Senha" 
                    name="senha"
                    rules={[{ required: true, message: 'Por favor, insira uma senha!' }]}>
                <Input />
                </Form.Item>
                <Button type="dashed" htmlType="submit">Cadastrar</Button>
            </Form>
        </div>

    )

}

export default CadastroAdmin