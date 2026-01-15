/* ***************************************************************
* Autor: Gabriel Marcone Magalhaes Santos
* Matricula........: 202410374
* Inicio...........: 13/03/2025
* Ultima alteracao.: 26/04/2025
* Nome.............: DataSingleton.java
* Funcao...........: Garantir o funcionamento e integrar as telas do programa,
  criando apenas uma instancia para toda a execucao.
*************************************************************** */
package util;

public class DataSingleton {
  private static DataSingleton instancia; // Armazena a unica instancia da classe
  private int opcaoPosicao; // Armazena a opcao de posicao selecionada na ChoiceBox pelo usuario
  private int opcaoSolucao; // Armazena a opcao de solucao de colisao selecionada na ChoiceBox pelo usuario
  /* ***************************************************************
  * Metodo: getInstancia
  * Funcao: Garantir que apenas uma instancia seja criada e usada durante todo programa,
    verificando se a instancia ja foi criada e, caso nao tenha sido, ele a cria.
  * Parametros: Sem parametros
  * Retorno: DataSingleton (a instancia unica da classe)
  *************************************************************** */
  public static DataSingleton getInstancia(){
    if(instancia == null){
      instancia = new DataSingleton(); // Criacao da instancia unica
    }
    return instancia;
  }
  /* ***************************************************************
  * Metodo: getOpcaoPosicao
  * Funcao: Retorna a opcao de posicao atualmente armazenada na instancia (escolha da ChoiceBox)
  * Parametros: Sem parametros
  * Retorno: int
  *************************************************************** */
  public int getOpcaoPosicao(){
    return opcaoPosicao;
  }
  /* ***************************************************************
  * Metodo: setOpcaoPosicao
  * Funcao: Determinar uma nova opcao para a posicao dos trens, caso o programa necessite
  * Parametros: int opcaoPosicao: Novo valor da opcao a ser armazenado
  * Retorno: void
  *************************************************************** */
  public void setOpcaoPosicao(int opcaoPosicao){
    this.opcaoPosicao = opcaoPosicao;
  }
  /* ***************************************************************
  * Metodo: getOpcaoSolucao
  * Funcao: Retorna a opcao de solucao de colisao atualmente armazenada na instancia (escolha da ChoiceBox)
  * Parametros: Sem parametros
  * Retorno: int
  *************************************************************** */
  public int getOpcaoSolucao(){
    return opcaoSolucao;
  }
  /* ***************************************************************
  * Metodo: setOpcaoSolucao
  * Funcao: Determinar uma nova opcao para a solucao de colisao, caso o programa necessite
  * Parametros: int opcaoSolucao: Novo valor da opcao a ser armazenado
  * Retorno: void
  *************************************************************** */
  public void setOpcaoSolucao(int opcaoSolucao){
    this.opcaoSolucao = opcaoSolucao;
  }
}