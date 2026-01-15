/* ***************************************************************
* Autor: Gabriel Marcone Magalhaes Santos
* Matricula........: 202410374
* Inicio...........: 12/03/2025
* Ultima alteracao.: 27/04/2025
* Nome.............: ControllerScene1.java
* Funcao...........: Controlar o funcionamento da primeira tela, do
  botao Iniciar e das choiceBoxes, com as opcoes de posicoes para os trens
  e as solucoes de colisao entre os trens.
*************************************************************** */
package controller;

import util.DataSingleton;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class ControllerScene1 implements Initializable {
  // Componentes da interface
  @FXML
  private Button botaoIniciar; // Botao responsavel por iniciar a proxima cena

  @FXML
  private ChoiceBox<String> choiceBoxPosicoesTrens; // ChoiceBox contendo as quatro opcoes de posicoes dos trens
  
  @FXML
  private ChoiceBox<String> choiceBoxSolucoesColisao; // ChoiceBox para selecionar a solucao de colisao entre os trens

  // Instancia unica da classe DataSingleton para compartilhar a escolha entre as telas
  DataSingleton dataSingleton = DataSingleton.getInstancia(); // Define uma instancia unica da classe DataSingleton para a solucao de colisao

  // Opcoes disponiveis na ChoiceBox de posicoes dos trens
  private String[] posicoesTrem = { 
    "Superior Esquerdo/Inferior Esquerdo",
    "Superior Direito/Inferior Direito",
    "Superior Esquerdo/Inferior Direito",
    "Superior Direito/Inferior Esquerdo"
  };

  // Opcoes disponiveis na ChoiceBox de solucoes de colisao
  private String[] solucoesColisao = { 
    "Variavel de Travamento",
    "Estrita Alternancia",
    "Solucao de Peterson"
  };
  
  /* ***************************************************************
  * Metodo: initialize
  * Funcao: Metodo sobrecarregado que inicializa a tela, carregando
    a ChoiceBox com as opcoes disponiveis e definindo a opcao
    previamente escolhida pelo usuario na sessao anterior.
  * Parametros: URL location: Localizacao do arquivo FXML,
    ResourceBundle resources: Recursos para internacionalizacao.
  * Retorno: void
  *************************************************************** */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    choiceBoxPosicoesTrens.getItems().addAll(posicoesTrem); // Adiciona as opcoes no ChoiceBox
    choiceBoxPosicoesTrens.setValue(choiceBoxPosicoesTrens.getItems().get(dataSingleton.getOpcaoPosicao())); // Define o valor inicial como o escolhido anteriormente

    choiceBoxSolucoesColisao.getItems().addAll(solucoesColisao); // Adiciona as opcoes no ChoiceBox
    choiceBoxSolucoesColisao.setValue(choiceBoxSolucoesColisao.getItems().get(dataSingleton.getOpcaoSolucao())); // Define o valor inicial como o escolhido anteriormente
  }
  /* ***************************************************************
  * Metodo: acaoBotaoIniciar
  * Funcao: Armazena a opcao selecionada pelo usuario e muda para a segunda tela.
  * Parametros: ActionEvent event: Evento disparado pelo botao "Iniciar".
  * Retorno: void
  *************************************************************** */
  @FXML
  void acaoBotaoIniciar(ActionEvent event) {
    try {
      dataSingleton.setOpcaoPosicao(choiceBoxPosicoesTrens.getSelectionModel().getSelectedIndex()); // Armazena a opcao escolhida na instancia unica de DataSingleton
      dataSingleton.setOpcaoSolucao(choiceBoxSolucoesColisao.getSelectionModel().getSelectedIndex()); // Armazena a opcao escolhida na instancia unica de DataSingleton
      Parent root = FXMLLoader.load(getClass().getResource("../view/scene2.fxml")); // Carrega o arquivo FXML da segunda cena (scene2.fxml) que contem o layout principal da aplicacao
      Scene scene = new Scene(root); // Obtem a janela atual e define a nova cena
      Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
      stage.setScene(scene);
      stage.setTitle("Trilhos de Trens do Minecraft");
    } catch (Exception exception) {
        System.out.println(exception.getMessage()); // Caso ocorra um erro ao carregar a interface, a mensagem de erro e exibida no console
    }
  }
}