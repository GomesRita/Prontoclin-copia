import { useState, useEffect } from 'react';
import axios from 'axios';
import { Table, Space, message, Button, Form, Select } from 'antd';
import { getToken } from '../../controle/cookie';
import dayjs from 'dayjs';
import utc from 'dayjs/plugin/utc';

function ConsultasPaciente() {
  const [showForm, setShowForm] = useState(false);
  const [formData, setFormData] = useState<DataType | null>(null);
  const [loading, setLoading] = useState(false);
  const [tableData, setTableData] = useState<DataType[]>([]); // Estado para a tabela
  const [availableDates, setAvailableDates] = useState<{ value: string; label: string }[]>([]);
  const [error, setError] = useState<string | null>(null);
  const [refreshData, setRefreshData] = useState(false);
  const [form] = Form.useForm();
  dayjs.extend(utc);

  interface DataType {
    key: string;
    nomeprofissionalsaude: string;
    especialidademedica: string;
    data: string;
  }

  const columns = [
    {
      title: 'Profissional',
      dataIndex: 'nomeprofissionalsaude',
      key: 'nomeprofissionalsaude',
      render: (text: string) => <a>{text}</a>,
      width: '16%',
    },
    {
      title: 'Especialidade Médica',
      dataIndex: 'especialidademedica',
      key: 'especialidademedica',
      width: '16%',
    },
    {
      title: 'Data da consulta',
      dataIndex: 'data',
      key: 'data',
      width: '16%',
      render: (text: string) => {
        const date = dayjs.utc(text);
        return <span>{date.format('DD/MM/YYYY HH:mm')}</span>;
      },
    },
    {
      title: 'Ação',
      key: 'action',
      render: (_: any, record: DataType) => (
        <Space size="middle">
          <Button type="dashed" onClick={() => handleReagendarClick(record)}>
            Reagendar
          </Button>
          <Button type="dashed" onClick={() => excluirConsulta(record)}>Excluir</Button>
        </Space>
      ),
      width: '10%',
    },
  ];

  //FUNÇÂO PARA DELETAR UMA CONSULTA
  const excluirConsulta = async (record: DataType) =>{
        setLoading(true);
        console.log(record.nomeprofissionalsaude)
        console.log(record.data)
        try{
            const token = getToken()
            if(token){
            await axios.put('http://localhost:8081/consulta/deletarConsulta',
                {
                    nomeprofissionalsaude: record.nomeprofissionalsaude,
                    dataconsulta: record.data
                }, 
                {
                headers:{
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`, 
                }
            });
            message.success("Consulta excluída")
            setRefreshData(prev => !prev); 
            }

        }catch(err){
            setError("Erro ao excluir consulta"),
            message.error("Erro ao excluir consulta")
        } finally{
            setLoading(false);
        }
    }

    const atualizarConsulta = async (values: any) =>{
      setLoading(true);
      try{
          const token = getToken()
          if(token){
          await axios.put('http://localhost:8081/consulta/atualizarConsulta',
              {
                idconsulta: formData?.key, // Certifique-se de passar o ID correto da consulta
                nomeprofissionalsaude: formData?.nomeprofissionalsaude, // Pega o nome do profissional do formulário
                dataconsulta: values.data, // Pega a data selecionada do formulário
              }, 
              {
              headers:{
                  'Content-Type': 'application/json',
                  'Authorization': `Bearer ${token}`, 
              }
          });
          message.success("Consulta reagendada com sucesso")
          setRefreshData(prev => !prev); 
          }
      }catch(err){
          setError("Erro ao reagendar consulta"),
          message.error("Erro ao reagendar consulta")
      } finally{
          setLoading(false);
      }
  }

  // FUNÇÃO PARA PEGAR AS DATAS DISPONÍVEIS PARA O SELECT
  const handleReagendarClick = async (record: DataType) => {
    setFormData(record);
    setShowForm(true);
    form.resetFields();  // Limpa os campos do formulário ao abrir

    const token = getToken();
    if (token) {
      try {
        setLoading(true);
        const response = await axios.post(
          'http://localhost:8081/consulta/agendaprofissional',
          { nomeprofissionalsaude: record.nomeprofissionalsaude },
          {
            headers: {
              'Content-Type': 'application/json',
              'Authorization': `Bearer ${token}`,
            },
          }
        );

        const transformedDates = response.data.map((item: any) => {
          const utcDate = dayjs.utc(item.dataconsulta); // Mantém a data em UTC
          return {
            value: utcDate.format('YYYY-MM-DDTHH:mm:ssZ'), // Valor em UTC no formato ISO
            label: utcDate.format('DD/MM/YYYY HH:mm'), // Exibe data amigável para o usuário
          };
        });

        // Atualiza as datas disponíveis para reagendamento
        setAvailableDates(transformedDates);
      } catch (err) {
        message.error('Erro ao carregar as datas disponíveis');
      } finally {
        setLoading(false);
      }
    }
  };

  // FUNÇÃO PARA CARREGAR OS DADOS DA TABELA
  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      setError(null);
  
      try {
        const token = getToken();
        if (token) {
          const response = await axios.get('http://localhost:8081/consulta/paciente/consultas', {
            headers: {
              'Authorization': `Bearer ${token}`,
            },
          });
  
          // Verifica se a resposta é um array e se está vazia
          if (Array.isArray(response.data)) {
            if (response.data.length === 0) {
              // Se não houver consultas, limpa a tabela e exibe uma mensagem
              setTableData([]);
              message.info('Nenhuma consulta agendada.');
            } else {
              // Se houver consultas, transforma os dados e atualiza a tabela
              const transformedTableData = response.data.map((item: any) => ({
                key: item.idconsulta,
                nomeprofissionalsaude: item.nomeprofissionalsaude,
                especialidademedica: item.especialidademedica,
                data: item.dataconsulta,
              }));
              setTableData(transformedTableData);
            }
          } else {
            // Se a resposta não for um array, define tableData como vazio
            setTableData([]);

          }
        } else {
          setError('Token não encontrado');
          message.error('Token não encontrado.');
        }
      } catch (err) {
        setError('Erro ao carregar os dados');
        message.error('Erro ao carregar os dados');
      } finally {
        setLoading(false);
      }
    };
  
    fetchData();
  }, [refreshData]);

  useEffect(() => {
    if (formData) {
      form.setFieldsValue({
        nomeprofissionalsaude: formData.nomeprofissionalsaude,
        data: undefined,  // Mantém o campo data sem valor selecionado inicialmente
      });
    }
  }, [formData, form]);
  

  return (
    <div>
      {showForm && (
        <Form
          form={form}
          name="dependencies"
          layout="vertical"
          labelCol={{ span: 100 }}
          wrapperCol={{ span: 100 }}
          style={{ width: '50%' }}
          onFinish={atualizarConsulta}
        >
          <h2 style={{ color: '#262626' }}>Reagendar Consulta</h2>
          <p>
            {formData && formData.nomeprofissionalsaude && formData.data ? 
              `Reagendar consulta com ${formData.nomeprofissionalsaude} no dia ${dayjs.utc(formData.data).format('DD/MM/YYYY HH:mm')}` 
              : 'Carregando informações da consulta...'}
          </p>
          <Form.Item
            label="Data da consulta"
            name="data"
            rules={[{ required: true, message: 'Por favor, selecione uma data!' }]}
          >
            <Select
              placeholder="Selecione uma data"
              loading={loading}
              options={availableDates} // Passa as datas como opções
            />
          </Form.Item>

          <Button
            type="dashed"
            htmlType="submit"
            disabled={loading || availableDates.length === 0}
          >
            Salvar
          </Button>
        </Form>
      )}

      {loading && <div>Carregando...</div>}
      {error && <div>Erro ao carregar os dados: {error}</div>}

      <Table<DataType> columns={columns} dataSource={tableData} />
    </div>
  );
}

export default ConsultasPaciente;
