idtagreplacer
=============

編集記号で書かれたテキストを、InDesignのタグ付きテキストに変換するプログラムです。

背景
----------

WEB+DB PRESS編集部で今も現役で使用しているプログラムですが、メンテができなくなっており、@taichiさんの助言に従って残っていたコードをGitHubで公開しています。

md2inao.plとの関係や、これまでについて詳しくは、以下をご覧ください。

https://gist.github.com/inao/baea09bc6fc53551886b

上記にも書いていますが、将来的には、このプログラムを使用する「執筆記法→編集記法→InDesignテキスト」というフローではなく、「執筆記法→InDesignテキスト」に移行すべきだと考えています。

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

作者とお礼
----------

お名前を出してよいかどうかの確認がとれなかったのでお名前は記しませんが、GitHubに登録した段階のコードは、すべてWEB+DB PRESS編集部に元いた1人の編集者が2008年ごろに書きました。

https://github.com/inao/idtagreplacer/commit/0a2f57985b6416dc8e8096ee2105a763e6e606a4

今でも現役バリバリで大活躍中です。

当時使っていたWEB+DB PRESS本誌だけでなく、書籍でも使っています。

どうもありがとうございます！

LICENSE
----------

* ★検討中。md2inao.plと同様にSame as Perl？　Apacheとか？　依存ライブラリはrhino
だけっぽい★
