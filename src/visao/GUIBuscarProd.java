/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visao;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import modelo.ProdutoVO;
import servicos.ProdutoServicos;

/**
 *
 * @author Camila_Camera
 */
public class GUIBuscarProd extends javax.swing.JInternalFrame {
    //funcionalidade para padronizar as colunas da tabela para que nunca mudem
    DefaultTableModel dtm = new DefaultTableModel(
            new Object [] [] {},
            new Object [] {"Código", "Nome", "Valor Custo", "Quantidade"}
    );  
    
    public void preencherTabela() {
        try{
            ProdutoServicos ps = servicos.ServicosFactory.getProdutoServicos();
            
            ArrayList<ProdutoVO> prod = new ArrayList<>();
            
            prod = ps.buscar();
            
            //laço que percorre o arraylist auxiliar e busca em cad linha, preencher com dados de suas respectivas colunas
            //ou seja, retorna 1 produt por linha, dividido em colunas como "código, nome, valorcusto, quantidade".
            for(int i=0; i<prod.size(); i++) {
                dtm.addRow(new String[] {
                    String.valueOf(prod.get(i).getIdproduto()),
                    String.valueOf(prod.get(i).getNome()),
                    String.valueOf(prod.get(i).getValorCusto()),
                    String.valueOf(prod.get(i).getQuantidade())
                    });
            }//fecha for
            //seta o modelo da tabela e traz os seus respectivos resultados
            jTableResultado.setModel(dtm);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ops, algo deu errado!" +e.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void limparTabela() {
        //setar a quantidade de linhas para 0
        dtm.setNumRows(0);
    }
    
    public void deletarProduto() {
        try {
            //busca a linha que o user clicou na tabela
            int linha = jTableResultado.getSelectedRow();
            
            //se não há nenhuma linha selecionada, gera mensagem de erro
            if (linha == -1) {
                JOptionPane.showMessageDialog(null, "Você não selecionou uma linha para exclusão!", "Erro!", JOptionPane.ERROR_MESSAGE);
            //se não, pega o valor selecionado
            } else {
                ProdutoServicos ps = servicos.ServicosFactory.getProdutoServicos();
                String idProduto = (String) jTableResultado.getValueAt(linha, 0);
                int r = JOptionPane.showConfirmDialog(null, "Você realmente deseja excluir este dado?", "Confirmação", JOptionPane.WARNING_MESSAGE);
                if (r==JOptionPane.YES_OPTION) {
                    ps.deletarProduto(Long.parseLong(idProduto));
                    JOptionPane.showMessageDialog(null, "Exclusão realizada com sucesso!", "Confirmação", JOptionPane.INFORMATION_MESSAGE);  
                } else {
                  JOptionPane.showMessageDialog(null, "Sua exclusão não foi realizada!", "Erro!", JOptionPane.ERROR_MESSAGE);  
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ops, algo deu errado!" +e.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
        } 
    }    
        
    public void filtrar() {
        try{
            //verifica se o campo de pesquisar está vazio
            if(jtfPesquisar.getText().isEmpty()) {
                preencherTabela();
            } else {
                ProdutoServicos ps = servicos.ServicosFactory.getProdutoServicos();
                /* Buscando o valor da JComboBox. O método getSelectedItem
                    devolve um Object selecionado na JCombo */
                String pesquisa = (String) jcbTipoDePesquisar.getSelectedItem();
                
                //variável que armazenará a pesquisa
                String query = null;
                
                //testa o que o user selecionou para realizar a pesquisa no jComboBox
                if (pesquisa.equals("Código")) {
                    //complementa o código sql dentro do método filtrar na classe ProdutoDAO
                    query = "where idproduto = "+jtfPesquisar.getText();
                } else if (pesquisa.equals("Nome")) {
                    query = "where nome like '%"+jtfPesquisar.getText() +"%'";
                } else if (pesquisa.equals("Valor custo")) {
                    query = "where valorcusto like '%"+jtfPesquisar.getText() +"%'";
                } else if (pesquisa.equals("Quantidade")) {
                    query = "where quantidade = "+jtfPesquisar.getText();
                }
                
                ArrayList<ProdutoVO> prod = new ArrayList<>();
            
            prod = ps.filtrar(query);
            
            //laço que percorre o arraylist auxiliar e busca em cad linha, preencher com dados de suas respectivas colunas
            //ou seja, retorna 1 produt por linha, dividido em colunas como "código, nome, valorcusto, quantidade".
            for(int i=0; i<prod.size(); i++) {
                dtm.addRow(new String[] {
                    String.valueOf(prod.get(i).getIdproduto()),
                    String.valueOf(prod.get(i).getNome()),
                    String.valueOf(prod.get(i).getValorCusto()),
                    String.valueOf(prod.get(i).getQuantidade())
                    });
            }//fecha for
            //seta o modelo da tabela e traz os seus respectivos resultados
            jTableResultado.setModel(dtm);
            }
                        
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ops, algo deu errado!" +e.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
        }
            
    }
    
    public void cancelarProduto() {
        jtfIdProdutoAltera.setText(null);
        jtfNomeProdutoAltera.setText(null);
        jtfQuantidadeProdAltera.setText(null);
        jtfValorProdAltera.setText(null);
    }
    
    public void alterarDados() {
        int linha = jTableResultado.getSelectedRow();
        if (linha!=-1) {
            //busca os dados da linha selecionada e envia para cada jTextField da tela cadastrar
            jtfIdProdutoAltera.setText((String)jTableResultado.getValueAt(linha,0));
            jtfNomeProdutoAltera.setText((String)jTableResultado.getValueAt(linha,1));
            jtfValorProdAltera.setText((String)jTableResultado.getValueAt(linha,2));
            jtfQuantidadeProdAltera.setText((String)jTableResultado.getValueAt(linha,3));
        } else {
            JOptionPane.showMessageDialog(null, "Você não selecionou nenhuma linha ainda");
        }
    }
    
    public void ConfirmarAlteracao() {
        try {
        modelo.ProdutoVO pVo = new modelo.ProdutoVO();
        
        pVo .setIdproduto(Long.parseLong(jtfIdProdutoAltera.getText()));
        pVo.setNome(jtfNomeProdutoAltera.getText());
        pVo.setQuantidade(Integer.parseInt(jtfQuantidadeProdAltera.getText()));
        pVo.setValorCusto(Double.parseDouble(jtfValorProdAltera.getText()));
        
        ProdutoServicos ps = servicos.ServicosFactory.getProdutoServicos();
        ps.alterarProduto(pVo);
        JOptionPane.showMessageDialog(null, "Alteração realizada com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ops, houve algum erro na alteração do dado!"+e.getMessage());
        }
        
    }
    /**
     * Creates new form GUIPesquisarProd
     */
    public GUIBuscarProd() {
        initComponents();
        preencherTabela();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTableResultado = new javax.swing.JTable();
        jbExcluir = new javax.swing.JButton();
        jlPesquisar = new javax.swing.JLabel();
        jtfPesquisar = new javax.swing.JTextField();
        jcbTipoDePesquisar = new javax.swing.JComboBox<>();
        jbAlterar = new javax.swing.JButton();
        jPanelGUIBuscarProdAltera = new javax.swing.JPanel();
        jlNomeProdAltera = new javax.swing.JLabel();
        jlValorProdAltera = new javax.swing.JLabel();
        jlQuantidadeProdAltera = new javax.swing.JLabel();
        jtfNomeProdutoAltera = new javax.swing.JTextField();
        jtfValorProdAltera = new javax.swing.JTextField();
        jtfQuantidadeProdAltera = new javax.swing.JTextField();
        jbCancelarAlteracao = new javax.swing.JButton();
        jlIdProdutoAltera = new javax.swing.JLabel();
        jtfIdProdutoAltera = new javax.swing.JTextField();
        jbConfirmarAlteracao = new javax.swing.JButton();

        setClosable(true);

        jTableResultado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTableResultado);

        jbExcluir.setText("Excluir");
        jbExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbExcluirActionPerformed(evt);
            }
        });

        jlPesquisar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlPesquisar.setText("Pesquisar por:");

        jtfPesquisar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jtfPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfPesquisarActionPerformed(evt);
            }
        });
        jtfPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfPesquisarKeyReleased(evt);
            }
        });

        jcbTipoDePesquisar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Código", "Nome", "Valor custo", "Quantidade" }));
        jcbTipoDePesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbTipoDePesquisarActionPerformed(evt);
            }
        });

        jbAlterar.setText("Alterar");
        jbAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAlterarActionPerformed(evt);
            }
        });

        jPanelGUIBuscarProdAltera.setForeground(new java.awt.Color(102, 255, 204));

        jlNomeProdAltera.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlNomeProdAltera.setText("Nome do Produto:");

        jlValorProdAltera.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlValorProdAltera.setText("Valor do Produto:");

        jlQuantidadeProdAltera.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlQuantidadeProdAltera.setText("Quantidade:");

        jtfNomeProdutoAltera.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jtfValorProdAltera.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jtfQuantidadeProdAltera.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jbCancelarAlteracao.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jbCancelarAlteracao.setText("Cancelar");
        jbCancelarAlteracao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCancelarAlteracaoActionPerformed(evt);
            }
        });

        jlIdProdutoAltera.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jlIdProdutoAltera.setText("Identificação:");

        jtfIdProdutoAltera.setEditable(false);

        jbConfirmarAlteracao.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jbConfirmarAlteracao.setText("Confirmar alteração");
        jbConfirmarAlteracao.setEnabled(false);
        jbConfirmarAlteracao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbConfirmarAlteracaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelGUIBuscarProdAlteraLayout = new javax.swing.GroupLayout(jPanelGUIBuscarProdAltera);
        jPanelGUIBuscarProdAltera.setLayout(jPanelGUIBuscarProdAlteraLayout);
        jPanelGUIBuscarProdAlteraLayout.setHorizontalGroup(
            jPanelGUIBuscarProdAlteraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelGUIBuscarProdAlteraLayout.createSequentialGroup()
                .addGroup(jPanelGUIBuscarProdAlteraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelGUIBuscarProdAlteraLayout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(jPanelGUIBuscarProdAlteraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlNomeProdAltera)
                            .addComponent(jlValorProdAltera)
                            .addComponent(jlQuantidadeProdAltera)
                            .addComponent(jlIdProdutoAltera))
                        .addGap(124, 124, 124)
                        .addGroup(jPanelGUIBuscarProdAlteraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfIdProdutoAltera, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelGUIBuscarProdAlteraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jbCancelarAlteracao, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanelGUIBuscarProdAlteraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jtfNomeProdutoAltera, javax.swing.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
                                    .addComponent(jtfValorProdAltera)
                                    .addComponent(jtfQuantidadeProdAltera)))))
                    .addGroup(jPanelGUIBuscarProdAlteraLayout.createSequentialGroup()
                        .addGap(103, 103, 103)
                        .addComponent(jbConfirmarAlteracao)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelGUIBuscarProdAlteraLayout.setVerticalGroup(
            jPanelGUIBuscarProdAlteraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelGUIBuscarProdAlteraLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanelGUIBuscarProdAlteraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlIdProdutoAltera)
                    .addComponent(jtfIdProdutoAltera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanelGUIBuscarProdAlteraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlNomeProdAltera)
                    .addComponent(jtfNomeProdutoAltera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanelGUIBuscarProdAlteraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlValorProdAltera)
                    .addComponent(jtfValorProdAltera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelGUIBuscarProdAlteraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlQuantidadeProdAltera)
                    .addComponent(jtfQuantidadeProdAltera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52)
                .addGroup(jPanelGUIBuscarProdAlteraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbConfirmarAlteracao)
                    .addComponent(jbCancelarAlteracao))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 833, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jlPesquisar)
                                .addGap(29, 29, 29)
                                .addComponent(jtfPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jcbTipoDePesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jPanelGUIBuscarProdAltera, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(193, 193, 193)
                        .addComponent(jbAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(171, 171, 171)
                        .addComponent(jbExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlPesquisar)
                    .addComponent(jtfPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbTipoDePesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelGUIBuscarProdAltera, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(133, 133, 133))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbExcluirActionPerformed
        deletarProduto();
        limparTabela();
        preencherTabela();
    }//GEN-LAST:event_jbExcluirActionPerformed

    private void jtfPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfPesquisarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfPesquisarActionPerformed

    private void jcbTipoDePesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbTipoDePesquisarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbTipoDePesquisarActionPerformed

    private void jtfPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfPesquisarKeyReleased
        //procura automaticamente enquanto filtra o item
        limparTabela();
        filtrar();
    }//GEN-LAST:event_jtfPesquisarKeyReleased

    private void jbAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAlterarActionPerformed
       //seta o botão Confirmar Alteração para true (ativo) quando o user clicar em Alterar
        jbConfirmarAlteracao.setEnabled(true);
        alterarDados();
    }//GEN-LAST:event_jbAlterarActionPerformed

    private void jbCancelarAlteracaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCancelarAlteracaoActionPerformed
        cancelarProduto();
    }//GEN-LAST:event_jbCancelarAlteracaoActionPerformed

    private void jbConfirmarAlteracaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbConfirmarAlteracaoActionPerformed
        ConfirmarAlteracao();
        cancelarProduto();
        limparTabela();
        preencherTabela();
        //seta o botão Confirmar Alteração de volta para false (inativo) depois de feita a alteração
        jbConfirmarAlteracao.setEnabled(false);
    }//GEN-LAST:event_jbConfirmarAlteracaoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanelGUIBuscarProdAltera;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableResultado;
    private javax.swing.JButton jbAlterar;
    private javax.swing.JButton jbCancelarAlteracao;
    private javax.swing.JButton jbConfirmarAlteracao;
    private javax.swing.JButton jbExcluir;
    private javax.swing.JComboBox<String> jcbTipoDePesquisar;
    private javax.swing.JLabel jlIdProdutoAltera;
    private javax.swing.JLabel jlNomeProdAltera;
    private javax.swing.JLabel jlPesquisar;
    private javax.swing.JLabel jlQuantidadeProdAltera;
    private javax.swing.JLabel jlValorProdAltera;
    private javax.swing.JTextField jtfIdProdutoAltera;
    private javax.swing.JTextField jtfNomeProdutoAltera;
    private javax.swing.JTextField jtfPesquisar;
    private javax.swing.JTextField jtfQuantidadeProdAltera;
    private javax.swing.JTextField jtfValorProdAltera;
    // End of variables declaration//GEN-END:variables
}
