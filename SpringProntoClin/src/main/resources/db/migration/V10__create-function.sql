CREATE OR REPLACE FUNCTION preencher_agenda_profissional()
RETURNS TRIGGER AS $$
DECLARE
data_inicio timestamp;
    data_fim timestamp;
    data_atual timestamp;
    horario_consulta timestamp;
BEGIN
    -- Define o período de preenchimento da agenda (por exemplo, 1 mês a partir de hoje)
    data_inicio := date_trunc('day', NOW());
    data_fim := data_inicio + interval '1 month';

    -- Loop para percorrer cada dia no período
    data_atual := data_inicio;
    WHILE data_atual <= data_fim LOOP
        -- Verifica se o dia atual é de segunda a sexta-feira (1 = segunda, 5 = sexta)
        IF EXTRACT(DOW FROM data_atual) BETWEEN 1 AND 5 THEN
            -- Preenche horários das 8h às 12h com intervalos de 30 minutos
            FOR i IN 0..8 LOOP
                horario_consulta := data_atual + interval '8 hours' + interval '30 minutes' * i;

                -- Verifica se o horário já existe para evitar duplicações
                IF NOT EXISTS (
                    SELECT 1 FROM agenda
                    WHERE iduser = NEW.iduser AND dataconsulta = horario_consulta
                ) THEN
                    INSERT INTO agenda (iduser, dataconsulta, situacao)
                    VALUES (NEW.iduser, horario_consulta, 'disponivel');
END IF;
END LOOP;

            -- Preenche horários das 14h às 17h com intervalos de 30 minutos
FOR i IN 0..6 LOOP
                horario_consulta := data_atual + interval '14 hours' + interval '30 minutes' * i;

                -- Verifica se o horário já existe para evitar duplicações
                IF NOT EXISTS (
                    SELECT 1 FROM agenda
                    WHERE iduser = NEW.iduser AND dataconsulta = horario_consulta
                ) THEN
                    INSERT INTO agenda (iduser, dataconsulta, situacao)
                    VALUES (NEW.iduser, horario_consulta, 'disponivel');
END IF;
END LOOP;
END IF;

        -- Avança para o próximo dia
        data_atual := data_atual + interval '1 day';
END LOOP;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_preencher_agenda
    AFTER INSERT ON profissionalsaude
    FOR EACH ROW
    EXECUTE FUNCTION preencher_agenda_profissional();

