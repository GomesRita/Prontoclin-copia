import  { useState, useEffect } from 'react';
import axios from 'axios';
import { getToken } from '../../controle/cookie';
import { Descriptions, Button, Flex,Space} from 'antd';
import CadastroAdmin from '../Cadastro/CadastroAdmin'; // Formulário de cadastro de administrador
import CadastroProSaude from '../Cadastro/CadastroProSaude';
import ListaProfissionais from '../Listas/ProfissionaisSaude';
import { useNavigate } from 'react-router-dom';
import { removeToken } from '../../controle/cookie'; // Importe a função removeToken que você já criou
import EditarAdmin from '../Edit/EditAdm';


function AdmIncial(){
    const [userData, setUserData] = useState<any>(null); 
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const navigate = useNavigate();
    const [conteudo, setConteudo] = useState(''); 
    const logout = () => {
        removeToken();
        navigate('/'); 
      };

    const handleClick = (tipo: any) => {
      setConteudo(tipo);
    };
    useEffect(() => {
    
        const fetchData = async () => {
            try {
                const token = getToken(); // Recupera o token do cookie
                if (token) {
                const response = await axios.get('http://localhost:8081/adm/me', {
                    headers: {
                    'Authorization': `Bearer ${token}`, 
                    },
                    withCredentials: true,
                });
                setUserData(response.data);
                } else {
                setError('Token não encontrado');
                }
                setLoading(false);
            } catch (err) {
                setError('Erro ao carregar os dados');
                setLoading(false);
            }
            };
        
            fetchData();
    }, []);
 

    if (loading) {
        return <div>Carregando...</div>;
    }
    
    
    if (error) {
        return <div>Erro ao carregar os dados: {error}</div>;
    }

    return (
        <>
        <Space direction="vertical" size={50} style={{ 
            display: 'flex', 
            alignItems: 'center',
            justifyContent: 'center', 
            background: '#ffffff', 
            padding: '20px', 
            borderRadius: '10px',
            boxShadow: '0 4px 10px rgba(0, 0, 0, 0.2)',
            width: '80%',
            left: '50%', /* Centraliza horizontalmente */
            transform: 'translate(10%,0%)'
             }}>
            <Space direction='horizontal' size="middle" style={{display: 'flex', justifyContent: 'center'}}>
                <Button type="dashed" onClick={() => handleClick('/editAdmin')}>Editar Dados</Button>
                <Button type="dashed" onClick={logout}>Sair</Button>
            </Space>
            <div style={{justifyItems: 'center'}}>
            <Descriptions title="Administrador" style={{ justifyItems: 'center', width: '50%'}}>
                <Descriptions.Item label="Nome" style={{textAlign: 'center'}}>{userData.nome}</Descriptions.Item>
                <Descriptions.Item label="CPF"  style={{textAlign: 'center'}}>{userData.cpf}</Descriptions.Item>
                <Descriptions.Item label="Email" style={{textAlign: 'center'}}>{userData.email}</Descriptions.Item>
            </Descriptions>
            </div>
            <Flex gap="small" wrap style={{ justifyContent: 'center'}}>
                <Button type="primary" onClick={() => handleClick('/cadastroAdmin')}>Cadastrar Administrador</Button>
                <Button type="primary" onClick={() => handleClick('/cadastroProSaude')}>Cadastrar Profissional de Saude</Button>
                <Button type="primary" onClick={() => handleClick('/listarProfissionais')}>Exibir Profissionais</Button>
            </Flex>
        </Space>
        <div style={{ width: '80%', transform: 'translate(13%,5%)', textAlign: "center"}}>
            {/* Renderiza o conteúdo com base no estado */}
            {conteudo === '/cadastroAdmin' && <CadastroAdmin/>}
            {conteudo === '/cadastroProSaude' && <CadastroProSaude/>}
            {conteudo === '/listarProfissionais' && <ListaProfissionais/>}
            {conteudo === '/editAdmin' && <EditarAdmin/>}
        </div>
        </>
    );

}

export default AdmIncial;
