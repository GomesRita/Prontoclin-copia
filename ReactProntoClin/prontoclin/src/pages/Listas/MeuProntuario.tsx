import { useEffect, useState } from "react";
import { getToken } from "../../controle/cookie";
import axios from "axios";
import { Badge, Col, Descriptions, Row } from "antd";

function MeuProntuario(){
    const [, setUserData] = useState<any>(null); 
    const [, setLoading] = useState(true);
    const [, setError] = useState<string | null>(null);
    
    const items = [
        {
          key: '1',
          label: 'Nome',
          children: 'João Silva',
        },
        {
          key: '2',
          label: 'Nome Social',
          children: 'Joãozinho',
        },
        {
          key: '3',
          label: 'Data Nascimento',
          children: '01/01/1990',
        },
        {
          key: '7',
          label: 'Sexo',
          children: 'Masculino',
        },
        {
          key: '8',
          label: 'CPF',
          children: '123.456.789-00',
        },
        {
          key: '9',
          label: 'Telefone',
          children: '(11) 98765-4321',
        },
        {
          key: '10',
          label: 'Queixa principal',
          children: 'Dor de cabeça',
        },
        {
          key: '11',
          label: 'Diagnóstico',
          children: 'Enxaqueca',
        },
        {
          key: '12',
          label: 'Situação Tratamento',
          span: 3,
          children: <Badge status="processing" text="Em andamento" />,
        },
        {
          key: '13',
          label: 'Histórico Médico',
          children: (
            <>
              <p>Histórico de doenças: Nenhum</p>
              <p>Medicamentos em uso: Nenhum</p>
            </>
          ),
        },
        {
          key: '14',
          label: 'Alergias',
          children: 'Nenhuma conhecida',
        },
        {
          key: '15',
          label: 'Prescrição médica',
          children: 'Paracetamol 500mg - 3x ao dia',
        },
      ];
      
      
      
    useEffect(() => {
   
        const fetchData = async () => {
           try {
               const token = getToken(); // Recupera o token do cookie
               if (token) {
                 const response = await axios.get('http://localhost:8081/paciente/me', {
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

         return(
            <>
        <Descriptions title="Detalhes do Paciente" bordered layout="vertical">
        <Descriptions.Item label="Nome">
          {items[0].children}
        </Descriptions.Item>
        <Descriptions.Item label="Nome Social">
          {items[1].children}
        </Descriptions.Item>
        <Descriptions.Item label="Data Nascimento">
          {items[2].children}
        </Descriptions.Item>
        {/* Sexo, CPF, Telefone em uma linha */}
        <Row>
          <Col span={8}>
            <Descriptions.Item label="Sexo">{items[3].children}</Descriptions.Item>
          </Col>
          <Col span={8}>
            <Descriptions.Item label="CPF">{items[4].children}</Descriptions.Item>
          </Col>
          <Col span={8}>
            <Descriptions.Item label="Telefone">{items[5].children}</Descriptions.Item>
          </Col>
        </Row>
        {/* Queixa principal e Diagnóstico na mesma linha */}
        <Row>
          <Col span={12}>
            <Descriptions.Item label="Queixa principal">
              {items[6].children}
            </Descriptions.Item>
          </Col>
          <Col span={12}>
            <Descriptions.Item label="Diagnóstico">
              {items[7].children}
            </Descriptions.Item>
          </Col>
        </Row>
        {/* Situação, Histórico Médico, Alergias, Prescrição médica em linhas separadas */}
        <Descriptions.Item label="Situação Tratamento" span={24}>
          {items[8].children}
        </Descriptions.Item>
        <Descriptions.Item label="Histórico Médico" span={24}>
          {items[9].children}
        </Descriptions.Item>
        <Descriptions.Item label="Alergias" span={24}>
          {items[10].children}
        </Descriptions.Item>
        <Descriptions.Item label="Prescrição médica" span={24}>
          {items[11].children}
        </Descriptions.Item>
      </Descriptions>
            </>
         );
}

export default MeuProntuario;