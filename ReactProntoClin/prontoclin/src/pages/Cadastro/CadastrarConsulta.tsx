import { useEffect, useState} from 'react';
import { Form, message, Select, Button, Space, Table} from 'antd';
import { getToken } from '../../controle/cookie';
import axios from 'axios';
import dayjs from 'dayjs';
import utc from 'dayjs/plugin/utc'; // Para manipulação de datas em UTC

function CadastroConsulta(){
    const [profissionais, setProfissionais] = useState<any[]>([]);
    const [ loading, setLoading] = useState(false); 
    const [ error,setError] = useState<string | null>(null); 
    const [data, setData] = useState<DataType[]>([]);
    dayjs.extend(utc);

    interface DataType {
        key: string;
        nomeprofissionalsaude: string;
        data: string;
      }
      
      const columns = [
        {
          title: 'Profissional',
          dataIndex: 'nomeprofissionalsaude',
          key: 'nomeprofissionalsaude',
          render: (text: string) => <a>{text}</a>,
          width: '16%'
        },
        {
          title: 'Datas Disponíveis',
          dataIndex: 'data',
          key: 'data',
          width: '16%',
          render: (text: string) => {
            const date = dayjs.utc(text);
            return <span>{date.format('DD/MM/YYYY HH:mm')}</span>;
            }
        },
        {
          title: 'Agendar',
          key: 'action',
          render: (_: any, record: DataType) => (
            <Space size="middle">
              <Button 
                type="dashed"
                onClick={() => agenda(record)} 
              >
                Agendar Consulta
              </Button>
            </Space>
          ),
          width: '10%'
        },
      ];

    const agenda = async (record: DataType) =>{
        setLoading(true);
        try{
            const token = getToken()
            if(token){
            await axios.post('http://localhost:8081/consulta',
                {
                    nomeProfissionalSaude: record.nomeprofissionalsaude,
                    dataConsulta: record.data
                }, 
                {
                headers:{
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`, 
                }
            });
            message.success("Consulta cadastrada com sucesso")
            }
        }catch(err){
            setError("Erro ao cadastrar consulta"),
            message.error("Erro ao cadastrar consulta")
        } finally{
            setLoading(false);
        }
    }
    
    const onFinish = async (values: { nome: string}) => {
      setLoading(true); 
      setError(null);
      console.log('Nome: ' + values.nome)
      const token = getToken(); 
      if (!token) {
          message.error('token não encontrado')
      }
      try {
          message.success('token encontrado')
          const response = await axios.post('http://localhost:8081/consulta/agendaprofissional', 
              {
                  nomeprofissionalsaude: values.nome,  // Parâmetro na URL
              },{
              headers: {
                  'Content-Type': 'application/json',
                  'Authorization': `Bearer ${token}`  // Token de autenticação
              } }
          );

          const transformedData = response.data.map((item: any) => ({
              key: item.idagenda,
              nomeprofissionalsaude: item.profissionalSaude.nomeprofissionalsaude, 
              data: item.dataconsulta
            }));

            setData(transformedData); 
          console.log(response.data)
          setError(null); 
      } catch (err) {
          setError('Erro ao acessar agenda');
          message.error('Erro ao acessar agenda'); 
      } finally {
          setLoading(false); 
      }
  };

    useEffect(() => {
        const fetchData = async () => {
            const token = getToken();
            if (token) {
              try {
                const response = await axios.get(
                  'http://localhost:8081/profSaude/profissionais',
                  {
                    headers: {
                      'Authorization': `Bearer ${token}`,
                    },
                  }
                );
                setProfissionais(response.data);
              } catch (error) {
                console.error('Erro ao buscar profissionais:', error);
                message.error('Erro ao buscar profissionais');
              }
            } else {
              message.error('Token não encontrado');
            }
        }
            fetchData()

      }, []);
      if (loading) {
        return <div>Carregando...</div>;
    }
    
    
    if (error) {
        return <div>Erro ao carregar os dados: {error}</div>;
    }

    return (
        <div>
            <h2 style={{ color: '#262626' }}>Cadastro de Profissionais de Saúde</h2>
            <Form
                name="layout-multiple-vertical"
                layout="vertical"
                labelCol={{ span: 4 }}
                wrapperCol={{ span: 20 }}
                onFinish={onFinish}
                >
                <Form.Item
                    label="Profissional Saude"
                    name="nome"
                    rules={[{ required: true, message: 'Please input!' }]}
                >
                    <Select
                    placeholder="Selecione um profissional"
                    loading={loading} // Mostra o ícone de carregamento enquanto os dados não foram carregados
                    >
                        {profissionais.map((profissional) => (
                            <Select.Option key={profissional.iduser} value={profissional.nomeprofissionalsaude}>
                                {profissional.nomeprofissionalsaude}, {profissional.especialidademedica}
                            </Select.Option>
                        ))}
                    </Select>
                </Form.Item>
                <Button type="dashed" htmlType="submit">Acessar Agenda</Button>
                </Form>
                {loading && <div>Carregando...</div>}
                {error && <div>Erro ao carregar os dados: {error}</div>}
                <Table<DataType> columns={columns} dataSource={data} />
        </div>

    )

}

export default CadastroConsulta;