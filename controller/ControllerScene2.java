/* ***************************************************************
* Autor: Gabriel Marcone Magalhaes Santos
* Matricula........: 202410374
* Inicio...........: 12/03/2025
* Ultima alteracao.: 04/05/2025
* Nome.............: ControllerScene2.java
* Funcao...........: Controlar o funcionamento da segunda tela,
  juntamente com o funcionamento dos botoes de voltar, reset e os 
  sliders para controlar a velocidade dos trens.
*************************************************************** */
package controller;

import util.DataSingleton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.ThreadTrem1;
import model.ThreadTrem2;

public class ControllerScene2 implements Initializable {
  @FXML
  private Button botaoResetar; // Botao para resetar a posicao e velocidade dos trens

  @FXML
  private Button botaoVoltar; // Botao para retornar a tela inicial

  @FXML
  private ImageView imagemTrem1; // Representacao visual do primeiro trem

  @FXML
  private ImageView imagemTrem2; // Representacao visual do segundo trem

  @FXML
  private Label lableVelocidadeTrem1; // Exibe a velocidade do primeiro trem
  private int velocidadeLable1; // Armazena a velocidade do primeiro trem

  @FXML
  private Label lableVelocidadeTrem2; // Exibe a velocidade do segundo trem
  private int velocidadeLable2; // Armazena a velocidade do segundo trem

  @FXML
  private Slider sliderTrem1; // Controla a velocidade do primeiro trem

  @FXML
  private Slider sliderTrem2; // Controla a velocidade do segundo trem

  DataSingleton dataSingleton = DataSingleton.getInstancia(); // Define uma instancia unica da classe DataSingleton 
  
  private ThreadTrem1 threadTrem1; // Cria um objeto do tipo ThreadTrem1
  private ThreadTrem2 threadTrem2; // Cria um objeto do tipo ThreadTrem2

  // Declaracao das variaveis de travamento
  public static volatile int variavelDeTravamento1 = 0;
  public static volatile int variavelDeTravamento2 = 0;

  // Declaracao das variaveis de estrita alternancia
  public static volatile int turno1EA = 0;
  public static volatile int turno2EA = 0;

  // Declaracao das variaveis da solucao de Peterson
  public static volatile int turno1SP;
  public static volatile boolean [] interesseRegiao1 = {false, false};
  public static volatile int turno2SP;
  public static volatile boolean [] interesseRegiao2 = {false, false};
  /* ***************************************************************
  * Metodo: initialize
  * Funcao: Metodo sobrecarregado que inicializa a tela com a
    opcao escolhida anteriormente na ChoiceBox, colocando cada
    trenzinho no seu respectivo local. Adiciona um listener
    para os sliders, para que os labels de velocidade sejam atualizados
    conforme a mudanca dos sliders.
  * Parametros: URL location, ResourceBundle resources
  * Retorno: void
  *************************************************************** */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    iniciarPosicaoTrens(); // Chama o metodo que define a posicao inicial dos trens de acordo com a opcao escolhida na tela anterior
    
    velocidadeLable1 = (int) sliderTrem1.getValue(); // Passa o valor do slider para uma variavel inteira velocidade
    lableVelocidadeTrem1.setText(Integer.toString(velocidadeLable1)); // Define o texto do label como a velocidade do trem padrao do slider

    sliderTrem1.valueProperty().addListener(new ChangeListener<Number>() {
      /* **************************************************************
      * Metodo: changed
      * Funcao: Metodo sobrescrito que atualiza o label de velocidade
       de acordo com a mudanca do slider.
      * Parametros: ObservableValue<? extends Number> observable, Number oldValue,
        Number newValue
      * Retorno: void
      *************************************************************** */
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        velocidadeLable1 = (int) (sliderTrem1.getValue()); // Passa o valor do slider, que eh variavel, para a velocidade
        lableVelocidadeTrem1.setText(Integer.toString(velocidadeLable1)); // Muda o label de acordo com a mudanca do slider
      }
    });
    velocidadeLable2 = (int) sliderTrem1.getValue(); // Passa o valor do slider para a velocidade
    lableVelocidadeTrem2.setText(Integer.toString(velocidadeLable2)); // Define o texto do label como a velocidade do trem padrao do slider

    sliderTrem2.valueProperty().addListener(new ChangeListener<Number>() {
      /* **************************************************************
       * Metodo: changed
       * Funcao: Metodo sobrescrito que atualiza o label de velocidade
         de acordo com a mudanca do slider.
       * Parametros: ObservableValue<? extends Number> observable, Number oldValue,
         Number newValue
       * Retorno: void
       *************************************************************** */
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        velocidadeLable2 = (int) sliderTrem2.getValue(); // Passa o valor do slider, que eh variavel, para a velocidade
        lableVelocidadeTrem2.setText(Integer.toString(velocidadeLable2)); // Muda o label de acordo com a mudanca do slider
      }
    });
    threadTrem1.start(); // Inicia o trem 1
    threadTrem2.start(); // Inicia o trem 2
  }
  /* ***************************************************************
  * Metodo: iniciarPosicaoTrens
  * Funcao: Inicia a posicao dos trens de acordo com a opcao escolhida
    na tela anterior. Cada caso representa uma opcao diferente de 
    posicao dos trens.
  * Parametros: Sem parametros
  * Retorno: void
  *************************************************************** */
  public void iniciarPosicaoTrens() {
    switch (dataSingleton.getOpcaoPosicao()) {
      case 0: {
        threadTrem1 = new ThreadTrem1(imagemTrem1, sliderTrem1, 0, dataSingleton.getOpcaoSolucao()); // Inicializa o Trem 1 no canto Superior Esquerdo
        threadTrem2 = new ThreadTrem2(imagemTrem2, sliderTrem2, 1, dataSingleton.getOpcaoSolucao()); // Inicializa o Trem 2 no canto Inferior Esquerdo
        break;
      }
      case 1: {
        threadTrem1 = new ThreadTrem1(imagemTrem1, sliderTrem1, 2, dataSingleton.getOpcaoSolucao()); // Inicializa o Trem 1 no canto Superior Direito
        threadTrem2 = new ThreadTrem2(imagemTrem2, sliderTrem2, 3, dataSingleton.getOpcaoSolucao()); // Inicializa o Trem 2 no canto Inferior Direito
        break;
      }
      case 2: {
        threadTrem1 = new ThreadTrem1(imagemTrem1, sliderTrem1, 0, dataSingleton.getOpcaoSolucao()); // Inicializa o Trem 1 no canto Superior Esquerdo
        threadTrem2 = new ThreadTrem2(imagemTrem2, sliderTrem2, 3, dataSingleton.getOpcaoSolucao()); // Inicializa o Trem 2 no canto Inferior Direito
        break;
      }
      case 3: {
        threadTrem1 = new ThreadTrem1(imagemTrem1, sliderTrem1, 2, dataSingleton.getOpcaoSolucao()); // Inicializa o Trem 1 no canto Superior Direto
        threadTrem2 = new ThreadTrem2(imagemTrem2, sliderTrem2, 1, dataSingleton.getOpcaoSolucao()); // Inicializa o Trem 2 no canto Inferior Esquerdo
        break;
      }
      default: {
        break;
      }
    }
  }
  /* ***************************************************************
  * Metodo: resetarVariaveisColisao
  * Funcao: Reseta as variaveis de controle das solucoes de colisao.
  * Parametros: Sem parametros
  * Retorno: void
  *************************************************************** */
  public void resetarVariaveisColisao() {
    // Reseta as variaveis de controle das solucoes
    variavelDeTravamento1 = 0;
    variavelDeTravamento2 = 0;

    turno1EA = 0;
    turno2EA = 0;
    
    turno1SP = 0;
    interesseRegiao1[0] = false;
    interesseRegiao1[1] = false;
    turno2SP = 0;
    interesseRegiao2[0] = false;
    interesseRegiao2[1] = false;
  }
  /* ***************************************************************
  * Metodo: acaoBotaoResetar
  * Funcao: Reseta a posicao, com a chamada do metodo resetarPosicaoTrens,
    e a velocidade dos trens para a posicao escolhida inicialmente.
  * Parametros: ActionEvent event
  * Retorno: void
  *************************************************************** */
  @FXML
  void acaoBotaoResetar(ActionEvent event) {
    // Para as threads dos trens
    threadTrem1.parar(); // Interrompe a thread do trem 1
    threadTrem2.parar(); // Interrompe a thread do trem 2

    // Reseta os labels de velocidade
    sliderTrem1.setValue(3);
    sliderTrem2.setValue(3);

    // Reseta as variaveis de controle das solucoes
    resetarVariaveisColisao();

    iniciarPosicaoTrens(); // Chama o metodo que define a posicao inicial dos trens de acordo com a opcao escolhida na tela anterior

    threadTrem1.start(); // Inicia o trem 1
    threadTrem2.start(); // Inicia o trem 2
  }
  /* ***************************************************************
  * Metodo: acaoBotaoVoltar
  * Funcao: Voltar para a tela inicial quando o botao for pressionado
  * Parametros: ActionEvent event
  * Retorno: void
  *************************************************************** */
  @FXML
  void acaoBotaoVoltar(ActionEvent event) {
    try {
      // Reseta as variaveis de controle das solucoes
      resetarVariaveisColisao();

      // Encerra as threads dos trens
      threadTrem1.parar(); // Interrompe a thread do trem 1
      threadTrem2.parar(); // Interrompe a thread do trem 2

      Parent root = FXMLLoader.load(getClass().getResource("../view/scene1.fxml")); // Carrega o .fxml da primeira tela
      Scene scene = new Scene(root); // Cria um novo objeto Scene
      Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow(); // Converte o objeto obtido inicialmente em um botao, e depois em um stage
      stage.setScene(scene); // Define a scene
      stage.setTitle("Trilhos de Trens do Minecraft");
      stage.show(); // Exibe a janela para o usuario
    } catch (Exception exception) {
        System.out.println(exception.getMessage());
    }
  }
}