import React, { createContext, useState } from 'react';

// Cria o contexto
export const DataContext = createContext({
  refreshTable: false,
  setRefreshTable: (() => {}) as React.Dispatch<React.SetStateAction<boolean>>,
});

// Provedor do contexto
export const DataProvider = ({ children }: { children: React.ReactNode }) => {
  const [refreshTable, setRefreshTable] = useState(false);

  return (
    <DataContext.Provider value={{ refreshTable, setRefreshTable }}>
      {children}
    </DataContext.Provider>
  );
};