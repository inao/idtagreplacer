idtagreplacer [![Build Status](https://travis-ci.org/inao/idtagreplacer.png)](https://travis-ci.org/inao/idtagreplacer)
=============

編集記号で書かれたテキストを、InDesignのタグ付きテキストに変換するプログラムです。

背景
----------

WEB+DB PRESS編集部で今も現役で使用しているプログラムですが、メンテができなくなっており、@taichiさんの助言に従って残っていたコードをGitHubで公開しています。

md2inao.plとの関係や位置付け、これまでについて詳しくは、以下をご覧ください。

https://gist.github.com/inao/baea09bc6fc53551886b

InDesign タグ付きテキストについて
----------

ユーザーガイドです。
以下はCS5のものなのですが、編集部で使っているのはCS3です。

http://help.adobe.com/ja_JP/indesign/cs/taggedtext/indesign_cs5_taggedtext.pdf

How to use
----------

manual.pdfをご覧ください。

サンプル
----------

sampleフォルダに、実行ファイルとサンプルの成果物を置いています。

* sample.md（Markdownテキスト）
 * ↓ md2inao.pl（ https://github.com/naoya/md2inao.pl ）を適用
* sample.txt（編集記号テキスト）
 * ↓ **idtagreplacer（本プログラム）を適用**
* sample_id.txt（InDesignタグ付きテキスト）
 * ↓ InDesignに取り込み
* sample_webdb.pdf（WEB+DB PRESS版）
* sample_book.pdf（書籍版）

初期の作者とお礼
----------

お名前を出してよいかどうかの確認がとれなかったのでお名前は記しませんが、GitHubに登録した段階のコードは、すべてWEB+DB PRESS編集部に元いた1人の編集者が2008年ごろに書きました。

https://github.com/inao/idtagreplacer/commit/0a2f57985b6416dc8e8096ee2105a763e6e606a4

お作りいただいたプログラムは、今でも現役バリバリで大活躍中です。

当時使っていたWEB+DB PRESS本誌だけでなく、書籍でも使っています。

どうもありがとうございます！

How to build
----------

### Make artifacts

```
gradlew build
```

### Make eclipse project

```
gradlew eclipse
```

LICENSE
----------

* Apache License 2.0
