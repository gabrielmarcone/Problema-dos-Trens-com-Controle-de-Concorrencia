/* ***************************************************************
* Autor: Gabriel Marcone Magalhaes Santos
* Matricula........: 202410374
* Inicio...........: 22/04/2025
* Ultima alteracao.: 04/05/2025
* Nome.............: ThreadTrem1
* Funcao...........: Criar e configurar a Thread que movimenta o trem de cima
*************************************************************** */
package model;

import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import controller.ControllerScene2;
import javafx.application.Platform;

public class ThreadTrem1 extends Thread {
  private int posicao;
  private double velocidadeX;
  private double velocidadeY;
  private ImageView imagemTrem;
  private Slider sliderTrem1; // Controla a velocidade do primeiro trem
  private int solucao; // Solucao escolhida pelo usuario (0, 1 ou 2)
  private int processoAtual = 0; // Variavel do processo atual (para o trem cinza = 0, para o trem preto = 1)
  private boolean regiaoCritica1 = false; // Indica se o trem esta na regiao critica 1
  private boolean regiaoCritica2 = false; // indica se o trem esta na regiao critica 2
  /* ***************************************************************
  * Metodo: ThreadTrem1 (Construtor)
  * Funcao: Inicializar um objeto da classe ThreadTrem1, definindo sua imagem e posicao inicial.
  * Parametros: ImageView imagemTrem: A imagem representando o trem, int posicao: Suas coordenadas no layout.
  * Retorno: void
  *************************************************************** */
  public ThreadTrem1(ImageView imagemTrem, Slider sliderTrem1, int posicao, int solucao) {
    this.imagemTrem = imagemTrem;
    this.posicao = posicao;
    this.sliderTrem1 = sliderTrem1; // Inicializa o slider para controlar a velocidade do trem
    this.solucao = solucao; // Inicializa a solucao escolhida pelo usuario
    switch(posicao) {
      case 0: { // Canto superior esquerdo
        imagemTrem.setLayoutX(-60);
        imagemTrem.setLayoutY(105);
        imagemTrem.setRotate(0);
        break;
      }
      /*
      case 1: { // Canto inferior esquerdo
        imagemTrem.setLayoutX(-60);
        imagemTrem.setLayoutY(166);
        imagemTrem.setRotate(0);
        break;
      }
      */
      case 2: { // Canto supeior direto
        imagemTrem.setLayoutX(602);
        imagemTrem.setLayoutY(100);
        imagemTrem.setRotate(0);
        break; 
      }
      /*
      case 3: { // Canto inferior direito
        imagemTrem.setLayoutX(602);
        imagemTrem.setLayoutY(168);
        imagemTrem.setRotate(0);
        break;
      }
      */
      default: {
        break;
      }
    }
  }
  /* ***************************************************************
  * Metodo: getPosicao
  * Funcao: Retorna a posicao atual do trem
  * Parametros: Sem parametros
  * Retorno: int
  *************************************************************** */
  public int getPosicao() {
    return posicao;
  }
  /* ***************************************************************
  * Metodo: getVelocidadeX
  * Funcao: Retorna a velocidade atual do trem no eixo X
  * Parametros: Sem parametros
  * Retorno: double
  *************************************************************** */
  public double getVelocidadeX() {
    return velocidadeX;
  }
  /* ***************************************************************
  * Metodo: setVelocidadeX
  * Funcao: Define uma nova velocidade para o trem no eixo X
  * Parametros: double velocidadeX: Novo valor da velocidade no eixo X.
  * Retorno: void
  *************************************************************** */
  public void setVelocidadeX(double velocidadeX) {
    this.velocidadeX = velocidadeX;
  }
  /* ***************************************************************
  * Metodo: getVelocidadeY
  * Funcao: Retorna a velocidade atual do trem no eixo Y
  * Parametros: Sem parametros
  * Retorno: double
  *************************************************************** */
  public double getVelocidadeY() {
    return velocidadeY;
  }
  /* ***************************************************************
  * Metodo: setVelocidadeY
  * Funcao: Define uma nova velocidade para o trem no eixo Y
  * Parametros: double velocidadeY:  Novo valor da velocidade no eixo Y
  * Retorno: void
  *************************************************************** */
  public void setVelocidadeY(double velocidadeY) {
    this.velocidadeY = velocidadeY;
  }
  /* ***************************************************************
  * Metodo: getImagemTrem
  * Funcao: Retorna a imagem que esta alocada no objeto trem
  * Parametros: Sem parametros
  * Retorno: ImageView (Objeto que representa visualmente o trem)
  *************************************************************** */
  public ImageView getImagemTrem() {
    return imagemTrem;
  }
  /* ***************************************************************
  * Metodo: entrarRegiaoCritica1
  * Funcao: Verifica se o trem pode entrar na regiao critica 1, dependendo da solucao escolhida.
  * Parametros: Sem parametros
  * Retorno: boolean (true se o trem pode entrar, false caso contrario)
  *************************************************************** */
  public boolean entrarRegiaoCritica1(int solucao) {
    switch (solucao) {
      case 0: { // Variavel de Travamento
        if (ControllerScene2.variavelDeTravamento1 == 1) {
          return false;
        } else {
          ControllerScene2.variavelDeTravamento1 = 1; // Ativa a variavel de travamento
          regiaoCritica1 = true; // Marca que o trem cinza esta na regiao critica 1
          return true; // Retorna true indicando que o trem cinza pode acessar a regiao critica 1
        }
      }
      case 1: { // Estrita Alternancia
        if (ControllerScene2.turno1EA == 1) {
          // Retorna falso se for o turno do outro trem
          return false; 
        } else {
          regiaoCritica1 = true; // Marca que o trem cinza esta na zona critica 1
          // Retorna true que o trem cinza pode acessar a zona critica 1
          return true;
        }
      }
      case 2: { // Solucao de Peterson
        int outroProcesso = 1 - processoAtual; // Define o outro processo (0 ou 1) - inverso do atual
        ControllerScene2.interesseRegiao1[processoAtual] = true; // Marca que o processo atual tem interesse na regiao critica 1
        ControllerScene2.turno1SP = processoAtual; // Define o turno para o processo atual
        // Verifica se o turno esta correto e se o outro processo esta interessado
        if (ControllerScene2.turno1SP == processoAtual && ControllerScene2.interesseRegiao1[outroProcesso] == true) {
            // Retorna falso se o outro processo estiver interessado na regiao critica 1 e o turno for do outro processo
            return false;
        } else {
            regiaoCritica1 = true; // Marca que o trem cinza esta na zona critica 1
            // Retorna true que o trem cinza pode acessar a zona critica 1
            return true;
        }
      }
      default: {
        return true;
      }
    }
  }
  /* ***************************************************************
  * Metodo: sairRegiaoCritica1
  * Funcao: Libera a regiao critica 1, dependendo da solucao escolhida.
  * Parametros: Sem parametros
  * Retorno: void
  *************************************************************** */
  public void sairRegiaoCritica1(int solucao) {
    switch (solucao) {
      case 0: { // Variavel de Travamento
        ControllerScene2.variavelDeTravamento1 = 0; // Libera a variavel de travamento
        regiaoCritica1 = false; // Indica que a regiao critica 1 nao esta mais ocupada
        break;
      }
      case 1: { // Estrita Alternancia
        ControllerScene2.turno1EA = 1; // Define que eh o outro turno do trem preto
        regiaoCritica1 = false; // Indica que a zona critica 1 nao esta mais ocupada
        break;
      }
      case 2: { // Solucao de Peterson
        ControllerScene2.interesseRegiao1[processoAtual] = false; // Marca que o processo atual nao tem mais interesse na regiao critica 1
        regiaoCritica1 = false; // Indica que a regiao critica 1 nao esta mais ocupada
        break;
      }
      default: {
        break;
      }
    }
  }
  /* ***************************************************************
  * Metodo: entrarRegiaoCritica2
  * Funcao: Verifica se o trem pode entrar na regiao critica 2, dependendo da solucao escolhida.
  * Parametros: Sem parametros
  * Retorno: boolean (true se o trem pode entrar, false caso contrario)
  *************************************************************** */
  public boolean entrarRegiaoCritica2(int solucao) {
    switch (solucao) {
      case 0: { // Variavel de Travamento
        if (ControllerScene2.variavelDeTravamento2 == 1) {
          return false;
        } else {
          ControllerScene2.variavelDeTravamento2 = 1; // Ativa a variavel de travamento
          regiaoCritica2 = true; // Marca que o trem esta na regiao critica 2
          return true; // Retorna true indicando que o trem pode acessar a regiao critica 2
        }
      }
      case 1: { // Estrita Alternancia
        if (ControllerScene2.turno2EA == 1) {
          // Retorna falso se for o turno do outro trem
          return false; 
        } else {
          regiaoCritica2 = true; // Marca que o trem cinza esta na zona critica 1
          // Retorna true que o trem cinza pode acessar a zona critica 1
          return true;
        }
      }
      case 2: { // Solucao de Peterson
        int outroProcesso = 1 - processoAtual; // Define o outro processo (0 ou 1) - inverso do atual
        ControllerScene2.interesseRegiao2[processoAtual] = true; // Marca que o processo atual tem interesse na regiao critica 2
        ControllerScene2.turno2SP = processoAtual; // Define o turno para o processo atual
        // Verifica se o turno esta correto e se o outro processo esta interessado
        if (ControllerScene2.turno2SP == processoAtual && ControllerScene2.interesseRegiao2[outroProcesso] == true) {
            // Retorna falso se o outro processo estiver interessado na regiao critica 2 e o turno for do outro processo
            return false;
        } else {
            regiaoCritica2 = true; // Marca que o trem cinza esta na zona critica 2
            // Retorna true que o trem cinza pode acessar a zona critica 2
            return true;
        }
      }
      default: {
        return true;
      }
    }
  }
  /* ***************************************************************
  * Metodo: sairRegiaoCritica2
  * Funcao: Libera a regiao critica 2, dependendo da solucao escolhida.
  * Parametros: Sem parametros
  * Retorno: void
  *************************************************************** */
  public void sairRegiaoCritica2(int solucao) {
    switch (solucao) {
      case 0: { // Variavel de Travamento
        ControllerScene2.variavelDeTravamento2 = 0; // Libera a variavel de travamento
        regiaoCritica2 = false; // Indica que a regiao critica 2 nao esta mais ocupada
        break;
      }
      case 1: { // Estrita Alternancia
        ControllerScene2.turno2EA = 1; // Define que eh o outro turno do trem preto
        regiaoCritica2 = false; // Indica que a zona critica 2 nao esta mais ocupada
        break;
      }
      case 2: { // Solucao de Peterson
        ControllerScene2.interesseRegiao2[processoAtual] = false; // Marca que o processo atual nao tem mais interesse na regiao critica 2
        regiaoCritica2 = false; // Indica que a regiao critica 2 nao esta mais ocupada
        break;
      }
      default: {
        break;
      }
    }
  }
  /* ***************************************************************
  * Metodo: animacaoTrem
  * Funcao: Atualiza a posicao do trem na tela, dependendo da sua velocidade e da sua posicao atual.
  * Parametros: int posicao: A posicao atual do trem (0 ou 2).
  * Retorno: void
  *************************************************************** */
  public void animacaoTrem(int posicao) {
    switch (posicao) {
      case 0: {
        // Inicio da animacao do trem
        if (this.getImagemTrem().getLayoutX() > -65 && this.getImagemTrem().getLayoutX() <= 65) {
          this.getImagemTrem().setLayoutX(this.getImagemTrem().getLayoutX() + this.getVelocidadeX());
        } // Primeira movimentacao do trem 1 na primeira secao de trilhos duplos

        /* **************************** INICIO REGIAO CRITICA 1 ******************************** */
        else if (this.getImagemTrem().getLayoutX() > 65 && this.getImagemTrem().getLayoutX() <= 95) {
          if (!entrarRegiaoCritica1(solucao) && !regiaoCritica1) {
            break; // Se o trem nao pode entrar na regiao critica 1, sai do loop
          }
          this.getImagemTrem().setLayoutX(this.getImagemTrem().getLayoutX() + this.getVelocidadeX());
          this.getImagemTrem().setLayoutY(this.getImagemTrem().getLayoutY() + this.getVelocidadeY());
          this.getImagemTrem().setRotate(30.3);
        } // Primeira curva do trem 1 para baixo
        else if (this.getImagemTrem().getLayoutX() > 95 && this.getImagemTrem().getLayoutX() <= 180) {
          this.getImagemTrem().setRotate(0);
          this.getImagemTrem().setLayoutX(this.getImagemTrem().getLayoutX() + this.getVelocidadeX());
        } // Movimentacao do trem 1 na primeira secao de trilhos simples
        else if (this.getImagemTrem().getLayoutX() > 180 && this.getImagemTrem().getLayoutX() <= 210) {
          this.getImagemTrem().setLayoutX(this.getImagemTrem().getLayoutX() + this.getVelocidadeX());
          this.getImagemTrem().setLayoutY(this.getImagemTrem().getLayoutY() - this.getVelocidadeY());
          this.getImagemTrem().setRotate(329.7);
        } // Primeira curva do trem 1 para cima (Fim da regiao critica 1)
        /* **************************** FIM REGIAO CRITICA 1 *********************************** */

        else if (this.getImagemTrem().getLayoutX() > 210 && this.getImagemTrem().getLayoutX() <= 325) {
          sairRegiaoCritica1(solucao); // Libera a regiao critica 1
          this.getImagemTrem().setRotate(0);
          this.getImagemTrem().setLayoutX(this.getImagemTrem().getLayoutX() + this.getVelocidadeX());
        } // Movimentacao do trem 1 na segunda secao de trilhos duplos

        /* **************************** INICIO REGIAO CRITICA 2 ******************************** */
        else if (this.getImagemTrem().getLayoutX() > 325 && this.getImagemTrem().getLayoutX() <= 355) {
          if (!entrarRegiaoCritica2(solucao) && !regiaoCritica2) {
            break; // Se o trem nao pode entrar na regiao critica 2, sai do loop
          }
          this.getImagemTrem().setLayoutX(this.getImagemTrem().getLayoutX() + this.getVelocidadeX());
          this.getImagemTrem().setLayoutY(this.getImagemTrem().getLayoutY() + this.getVelocidadeY());
          this.getImagemTrem().setRotate(30.3);
        } // Segunda curva do trem 1 para baixo
        else if (this.getImagemTrem().getLayoutX() > 355 && this.getImagemTrem().getLayoutX() <= 440) {
          this.getImagemTrem().setRotate(0);
          this.getImagemTrem().setLayoutX(this.getImagemTrem().getLayoutX() + this.getVelocidadeX());
        } // Movimentacao do trem 1 na segunda secao de trilhos simples
        else if (this.getImagemTrem().getLayoutX() > 440 && this.getImagemTrem().getLayoutX() <= 475) {
          this.getImagemTrem().setLayoutX(this.getImagemTrem().getLayoutX() + this.getVelocidadeX());
          this.getImagemTrem().setLayoutY(this.getImagemTrem().getLayoutY() - this.getVelocidadeY());
          this.getImagemTrem().setRotate(329.7);
        } // Segunda curva do trem 1 para cima (Fim da regiao critica 2)
        /* **************************** FIM REGIAO CRITICA 2 *********************************** */

        else if (this.getImagemTrem().getLayoutX() > 475 && this.getImagemTrem().getLayoutX() <= 602) {
          sairRegiaoCritica2(solucao); // Libera a regiao critica 2
          this.getImagemTrem().setRotate(0);
          this.getImagemTrem().setLayoutX(this.getImagemTrem().getLayoutX() + this.getVelocidadeX());
        } // Ultima movimentacao do trem 1 na terceira secao de trilhos duplos
        else {
          this.getImagemTrem().setLayoutX(-60);
          this.getImagemTrem().setLayoutY(105);
          this.getImagemTrem().setRotate(0);
        } // Volta o trem 1 para a posicao inicial
        // Fim da animacao do trem
        break;
      }
      case 2: {
        // Inicio da animacao do trem 1
        if (this.getImagemTrem().getLayoutX() <= 602 && this.getImagemTrem().getLayoutX() > 475) {
          this.getImagemTrem().setLayoutX(this.getImagemTrem().getLayoutX() - this.getVelocidadeX());
        } // Primeira movimentacao do trem 1 na primeira secao de trilhos duplos

        /* **************************** INICIO REGIAO CRITICA 2 ******************************** */
        else if (this.getImagemTrem().getLayoutX() <= 475 && this.getImagemTrem().getLayoutX() > 440) {
          if (!entrarRegiaoCritica2(solucao) && !regiaoCritica2) {
            break; // Se o trem nao pode entrar na regiao critica 2, sai do loop
          }
          this.getImagemTrem().setLayoutY(this.getImagemTrem().getLayoutY() + this.getVelocidadeY());
          this.getImagemTrem().setLayoutX(this.getImagemTrem().getLayoutX() - this.getVelocidadeX());
          this.getImagemTrem().setRotate(329.7);
        } // Primeira curva do trem 1 para baixo
        else if (this.getImagemTrem().getLayoutX() <= 440 && this.getImagemTrem().getLayoutX() > 355) {
          this.getImagemTrem().setRotate(0);
          this.getImagemTrem().setLayoutX(this.getImagemTrem().getLayoutX() - this.getVelocidadeX());
        } // Movimentacao do trem 1 na primeira secao de trilhos simples
        else if (this.getImagemTrem().getLayoutX() <= 355 && this.getImagemTrem().getLayoutX() > 325) {
          this.getImagemTrem().setLayoutY(this.getImagemTrem().getLayoutY() - this.getVelocidadeY());
          this.getImagemTrem().setLayoutX(this.getImagemTrem().getLayoutX() - this.getVelocidadeX());
          this.getImagemTrem().setRotate(30.3);
        } // Primeira curva do trem 1 para cima (Fim da regiao critica 2)
        /* **************************** FIM REGIAO CRITICA 2 *********************************** */

        else if (this.getImagemTrem().getLayoutX() <= 325 && this.getImagemTrem().getLayoutX() > 210) {
          sairRegiaoCritica2(solucao); // Libera a regiao critica 2
          this.getImagemTrem().setRotate(0);
          this.getImagemTrem().setLayoutX(this.getImagemTrem().getLayoutX() - this.getVelocidadeX());
        } // Movimentacao do trem 1 na segunda secao de trilhos duplos

        /* **************************** INICIO REGIAO CRITICA 1 ******************************** */
        else if (this.getImagemTrem().getLayoutX() <= 210 && this.getImagemTrem().getLayoutX() > 180) {
          if (!entrarRegiaoCritica1(solucao) && !regiaoCritica1) {
            break; // Se o trem nao pode entrar na regiao critica 1, sai do loop
          }
          this.getImagemTrem().setLayoutY(this.getImagemTrem().getLayoutY() + this.getVelocidadeY());
          this.getImagemTrem().setLayoutX(this.getImagemTrem().getLayoutX() - this.getVelocidadeX());
          this.getImagemTrem().setRotate(329.7);
        } // Segunda curva do trem 1 para baixo
        else if (this.getImagemTrem().getLayoutX() <= 180 && this.getImagemTrem().getLayoutX() > 95) {
          this.getImagemTrem().setRotate(0);
          this.getImagemTrem().setLayoutX(this.getImagemTrem().getLayoutX() - this.getVelocidadeX());
        } // Movimentacao do trem 1 na segunda secao de trilhos simples
        else if (this.getImagemTrem().getLayoutX() <= 95 && this.getImagemTrem().getLayoutX() > 65) {
          this.getImagemTrem().setLayoutY(this.getImagemTrem().getLayoutY() - this.getVelocidadeY());
          this.getImagemTrem().setLayoutX(this.getImagemTrem().getLayoutX() - this.getVelocidadeX());
          this.getImagemTrem().setRotate(30.3);
        } // Segunda curva do trem 1 para cima (Fim da regiao critica 2)
        /* **************************** FIM REGIAO CRITICA 2 *********************************** */
        
        else if (this.getImagemTrem().getLayoutX() <= 65 && this.getImagemTrem().getLayoutX() > -65) {
          sairRegiaoCritica1(solucao); // Libera a regiao critica 1
          this.getImagemTrem().setRotate(0);
          this.getImagemTrem().setLayoutX(this.getImagemTrem().getLayoutX() - this.getVelocidadeX());
        } // Ultima movimentacao do trem 1 na terceira secao de trilhos duplos
        else {
          this.getImagemTrem().setLayoutX(602);
          this.getImagemTrem().setLayoutY(100);
          this.getImagemTrem().setRotate(0);
        } // Volta o trem 1 para a posicao inicial
        // Fim da animacao do trem 1
        break;
      }
      default:
        break;
    }
  }
  /* ***************************************************************
  * Metodo: parar
  * Funcao: Interrompe a execucao da thread do trem, parando sua movimentacao.
  * Parametros: Sem parametros
  * Retorno: void
  *************************************************************** */
  public void parar() {
    this.interrupt(); // Se estiver dormindo, acorda
  }
  /* ***************************************************************
  * Metodo: run
  * Funcao: Executa o loop infinito que atualiza a posicao do trem com base na velocidade e no tempo.
  * Parametros: Sem parametros
  * Retorno: void
  *************************************************************** */
  @Override
  public void run() {
    while (!this.isInterrupted()) { // Loop infinito para atualizar a posicao do trem
      // Atualiza a posicao do trem com base na velocidade e no tempo
      setVelocidadeX(sliderTrem1.getValue()); // Atualiza a velocidade do trem no eixo X
      setVelocidadeY(sliderTrem1.getValue()); // Atualiza a velocidade do trem no eixo Y
      Platform.runLater(() -> {animacaoTrem(posicao);} ); // Atualiza a posicao do trem na interface grafica
      try {
        Thread.sleep(17); // Aguarda um tempo determinado pela velocidade do trem
      } catch (InterruptedException e) {
        System.out.println("Thread 1 finalizada com sucesso."); // Mensagem de sucesso ao finalizar a thread
        break; // Sai do loop se a thread for interrompida
      }
    } // Fim do loop infinito
  }
}