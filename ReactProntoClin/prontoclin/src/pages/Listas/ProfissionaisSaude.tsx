import { useState, useEffect } from 'react';
import axios from 'axios';
import { Table, Tag, Space, message, Button } from 'antd';
import { getToken } from '../../controle/cookie';


function ListaProfissionais() {
  interface DataType {
    key: string;
    nomeprofissionalsaude: string;
    especialidademedica: string;
    email: string;
    status: string;
  }
  
  const columns = [
    {
      title: 'Nome',
      dataIndex: 'nomeprofissionalsaude',
      key: 'nomeprofissionalsaude',
      render: (text: string) => <a>{text}</a>,
      width: '16%'
    },
    {
      title: 'Especialidade Médica',
      dataIndex: 'especialidademedica',
      key: 'especialidademedica',
      width: '16%'
    },
    {
      title: 'Email',
      dataIndex: 'email',
      key: 'email',
      width: '16%'
    },
    {
      title: 'Status',
      key: 'status',
      dataIndex: 'status',
      render: (_: string, { status }: { status: string }) => (
        <Tag color={status === 'ATIVO' ? 'green' : 'volcano'}>
          {status.toUpperCase()}
        </Tag>
      ),
      width: '16%'
    },
    {
      title: 'Action',
      key: 'action',
      render: (_: any, record: DataType) => (
        <Space size="middle">
          <Button 
            type="dashed" 
            onClick={() => onFinish(record)} 
          >
            Mudar Status
          </Button>
        </Space>
      ),
      width: '10%'
    },
  ];

  const [loading, setLoading] = useState(false);
  const [data, setData] = useState<DataType[]>([]);
  const [error, setError] = useState<string | null>(null);

  const onFinish = async (record: DataType) => {
    setLoading(true);
    try {
        const token = getToken(); 
        if (token) {
            await axios.put(
                'http://localhost:8081/profSaude/StatusProfissional',
                {
                  nomeprofissionalsaude: record.nomeprofissionalsaude
                },
                {
                    headers: {
                      'Content-Type': 'application/json',
                      'Authorization': `Bearer ${token}`, 
                    }
                }
  
            );
              message.success('Status atualizado com sucesso!');
              setData(prevData =>
                prevData.map(item =>
                  item.key === record.key ? { ...item, status: record.status === 'ATIVO' ? 'INATIVO' : 'ATIVO' } : item
                )
              );
        }
    } catch (err) {
        setError('Erro ao atualizar status');
        message.error('Erro ao atualizar status'); 
    } finally {
        setLoading(false); 
    }
  }

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      setError(null);

      try {
        const token = getToken(); 
        if (token) {
          const response = await axios.get('http://localhost:8081/profSaude/AllProfissionais', {
            headers: {
              'Authorization': `Bearer ${token}`,
            },
          });

          const transformedData = response.data.map((item: any) => ({
            key: item.nomeprofissionalsaude,
            nomeprofissionalsaude: item.nomeprofissionalsaude, 
            especialidademedica: item.especialidademedica, 
            email: item.email,
            status: item.status, 
          }));

          setData(transformedData); 
        } else {
          setError('Token não encontrado');
        }
      } catch (err) {
        setError('Erro ao carregar os dados');
        message.error('Erro ao carregar os dados');
      } finally {
        setLoading(false);
      }
    };

    fetchData(); 
  }, []);

  return (
    <div>
      {loading && <div>Carregando...</div>}
      {error && <div>Erro ao carregar os dados: {error}</div>}
      <Table<DataType> columns={columns} dataSource={data} />
    </div>
  );
}

export default ListaProfissionais;
