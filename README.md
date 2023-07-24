<img width=100% src="https://capsule-render.vercel.app/api?type=waving&color=4278e3&height=120&section=header"/>

# Teste Técnico - Quiz Dynamox 

## Resumo 
Este projeto trata-se de um teste técnico da empresa Dynamox no qual o objetivo era construir um quiz de 10 perguntas e respostas através de requisição API em um endpoint(HOST) que foi disponibilizado pela empresa.

## Imagens do App

<div align="center"> 
<img src = "https://user-images.githubusercontent.com/111225477/255665658-260d127c-7558-48ea-b6de-7d803c8b3cc4.png" width = "150px" >
<img src = "https://user-images.githubusercontent.com/111225477/255665664-28a2a0ff-c181-4792-abbc-645db0a10a26.png" width = "150px" >
<img src = "https://user-images.githubusercontent.com/111225477/255665668-8e01b19d-4714-4df5-8f94-0f42346093bb.png" width = "150px" >

</div>

</br>

## Requisitos exigidos pela Dynamox

- Ao abrir o aplicativo, o usuário insere o seu nome ou apelido e pressiona um botão
para iniciar o quiz.
- O quiz consiste de uma sequência de 10 perguntas de múltipla escolha.
- As perguntas serão obtidas por meio de uma requisição HTTP e serão recebidas em
formato JSON.
- O resultado de cada pergunta também será consultado via HTTP.
- O usuário deve saber se acertou a pergunta antes de passar para a próxima.
- Ao final das 10 perguntas, o aplicativo deve mostrar a pontuação do usuário e
oferecer opção de reiniciar o quiz.
- Para a entrega do projeto faça o upload de seu projeto para um repositório no Github
nos envie o link.

## Detalhes do desenvolvimento do App

Este App foi totalmente escrito na linguagem Kotlin, através da plataforma Android Studio. Utilizei o framework moderno, [Jetpack Compose](https://developer.android.com/jetpack/compose/documentation?hl=pt-br), para criação das telas de UI pois ele vem se mostrando muito útil além  de oferecer maior escalabilidade e fácil manutenção para novos projetos em Android Nativo.

Para fazer a requisições web GET( ) e POST( ), utilizei a biblioteca [Retrofit](https://square.github.io/retrofit/) juntamente com o [GSON](https://github.com/google/gson) para converter dados para o formato JSON e vice-versa.

Para a navegação de telas foi utilizada a biblioteca [Navigation Compose](https://developer.android.com/jetpack/compose?gclid=Cj0KCQjwwvilBhCFARIsADvYi7IyQi2pShJgnUOyuCsn34jZWwEBHOTBciat7Aa13kk0AYPWos4PkwUaApW_EALw_wcB&gclsrc=aw.ds&hl=pt-br) que é indicada pelo Google.


## Video demonstrativo

A seguir temos um GIF  que demonstra de forma resumida das funcionalidades do App. Para acessar o vídeo completo com audio, basta clicar no link ao lado: 
 [Link do vídeo](https://www.youtube.com/watch?v=VPA63Getvs0)

<div align="center">
<img src = "https://user-images.githubusercontent.com/111225477/255675447-49cbca34-a167-4ac2-bbd7-1efec4fc3c74.gif" width = "230px">

</div>
</br>

## Problemas encontrados
Encontrei um problema na questão de **ID 48**, é a pergunta que começa com o texto **"Quem foi o primeiro homem a pisar na lua? ... "**. O problema ocorre porque quando fazemos a requisição GET( ) o sevidor nos devolve a alternativa correta, **"Neils Armstrong, em 1969."** com um ponto final, mas quando usamos essa alternativa como requisição ao fazer o POST( ) o servidor reconhece essa alternativa como **errada** justamente por cauda so ponto final. Segue imagem abaixo.
<div align="center"> 
<img src = "https://user-images.githubusercontent.com/111225477/255669766-517c41c4-0b95-4739-8f12-154f0686ab47.png" width = "650px" >
</div>

Basta remover manualmente o ponto final e refazer a requisição POST( ) que o servidor reconhece a alternativa como **correta** retornando um "result : true" conforme requisição feita através do programa Insomnia logo abaixo:

<div align="center"> 
<img src = "https://user-images.githubusercontent.com/111225477/255669769-5d9c828a-dcb0-4a14-ac89-e36351b85814.png" width = "650px" >
</div>

## Sugestão de Melhoria na API
Acredito que uma simples conversa com o pessoal de Backend seria suficiente para corrigir esse problema, visto que o servidor está enviando a alternativa com um caracter adicional (o ponto final) na requisição GET( ) mas não aceita essa resposta com o ponto final na requisição POST( ). 

Sabemos qual o ID da questão problemática, sabemos qual a alternativa está incorreta e sabemos como corrigí-la, basta alterar essa alternativa no banco de dados removendo o ponto final e o problema estará resolvido.

## Agradecimentos
Fico grato por estar participando do processo seletivo para Desenvolvedor Android na empresa Dynamox, esse teste foi bastante desafiador e divertido, exigindo do candidato conhecimentos e formas de solucionar problemas aparentemente simples mas que na prática podem demandar conhecimentos específicos de certos temas.

Estou empolgado com hipótese de poder trabalhar e colaborar com uma empresa de alta tecnologia e que tem VALORES como: Respeito, Ética, Empatia e Comprometimento.

Nos vemos na próxima fase , um abraço.  😃
