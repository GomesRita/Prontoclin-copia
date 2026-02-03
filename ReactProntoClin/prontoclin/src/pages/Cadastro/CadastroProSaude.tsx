import { useState} from 'react';
import { Button, Form, Input, message   } from 'antd';
import { getToken } from '../../controle/cookie';
import axios from 'axios';


function CadastroProSaude(){
    const [ loading, setLoading] = useState(false); 
    const [ error,setError] = useState<string | null>(null); 

    const onFinish = async (values: { nome: string, cpf: string, especialidadeMedica: string, telefone: string, crm: string, email: string, senha: string }) => {
        setLoading(true); 
        setError(null);
        
        if (loading) {
            return <div>Carregando...</div>;
        }
        
        
        if (error) {
            return <div>Erro ao carregar os dados: {error}</div>;
        }
    
        try {
            const token = getToken(); 
            if (token) {
                const response = await axios.post(
                    'http://localhost:8081/auth/register/prosaude',
                    {
                        nomeProfissionalSaude: values.nome,
                        cpfProfissionalSaude: values.cpf,
                        especialidadeMedica: values.especialidadeMedica,
                        telefoneProfissionalSaude: values.telefone,
                        CRM: values.crm,
                        status: "ATIVO",
                        email: values.email,
                        senha: values.senha,
                        userrole: "PROFSAUDE"
                    },
                    {
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': `Bearer ${token}`, 
                        }
                    }

                );
                console.log(response.data)
                message.success('Profissional de saude cadastrado com sucesso!'); 
                setError('Token não encontrado');
            }
        } catch (err) {
            setError('Erro ao cadastrar profissional de saude');
            message.error('Erro ao cadastrar profissional de saude'); 
        } finally {
            setLoading(false); 
        }
    };

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
                <h2 style={{ color: '#262626' }}>Cadastro de Profissionais de Saúde</h2>
                <Form.Item label="Nome" name="nome" rules={[{ required: true }]}>
                <Input />
                </Form.Item>
                <Form.Item label="CPF" name="cpf" rules={[{ required: true }]}>
                <Input />
                </Form.Item>
                <Form.Item label="Especialidade médica" name="especialidadeMedica" rules={[{ required: true }]}>
                <Input />
                </Form.Item>
                <Form.Item label="Telefone" name="telefone" rules={[{ required: true }]}>
                <Input />
                </Form.Item>
                <Form.Item label="CRM" name="crm" rules={[{ required: true }]}>
                <Input />
                </Form.Item>
                <Form.Item label="Email" name="email" rules={[{ required: true }]}>
                <Input />
                </Form.Item>
                <Form.Item label="Senha" name="senha" rules={[{ required: true }]}>
                <Input />
                </Form.Item>
                <Button type="dashed" htmlType="submit">Cadastrar</Button>
            </Form>
        </div>

    )

}

export default CadastroProSaude