REM @echo off
REM ============================================================================
REM タイトル：KSPSPFTPデータ転送処理
REM 説明	：KSPSP転送用のファイルを転送します。
REM version 1.00 2011/02/03 Umemoto:新規 
REM ============================================================================

SET BATCHF_HOME=E:\mdware\uriage

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

REM コピー元ファイルディレクトリ
set COPYDIR="E:\mdware\datas\ftpdata\LocalUser\noauth\uriage\NCR"
REM コピー元ファイル名1
set INFILENAME1=SDPT.ftp
REM コピー元ファイル名2
set INFILENAME2=SMPU.ftp
REM コピー元ファイル名3
set INFILENAME3=IF_KSPSP_R_SYOHIN.csv
REM 出力ファイル名1
set OUTFILENAME1=KDPT.STR
REM 出力ファイル名2
set OUTFILENAME2=KMPU.STR
REM 出力ファイル名3
set OUTFILENAME3=IF_KSPSP_R_SYOHIN.csv
REM システム日付（スラッシュなし）
set SYSDATE=%DATE:~0,4%%DATE:~5,2%%DATE:~8,2%
REM FTPファイルディレクトリ
set FTPDIR="E:\mdware\datas\ftpdata\LocalUser\noauth\uriage\NCR"
REM FTPファイル
set FTPFILE=kspspftptemp.ftp
REM サーバー名
set server=60.32.112.67
REM ユーザ名
set user=ksprenkei78
REM パスワード
set password=pswrenkei78
REM FTP出力ディレクトリ
set FTPOUTDIR="/"
REM エラーログディレクトリ
set LOGDIR=%BATCHF_HOME%\log
REM エラーログ
set ERROR_LOG=kspspFtpsend.log

REM ファイルの存在チェック
IF not exist %COPYDIR%\%SYSDATE%%OUTFILENAME1% goto ERROR1
IF not exist %COPYDIR%\%SYSDATE%%OUTFILENAME2% goto ERROR1
IF not exist %COPYDIR%\%OUTFILENAME3% goto ERROR1

REM IF_KSPSP_R_SYOHINをリネーム
copy %COPYDIR%\%OUTFILENAME3% %COPYDIR%\%SYSDATE%%OUTFILENAME3%

REM 前日バックアップファイルの削除
del %COPYDIR%\BK_%OUTFILENAME1%
del %COPYDIR%\BK_%OUTFILENAME2%
del %COPYDIR%\BK_%OUTFILENAME3%

REM FTPログイン処理
echo open %server%> %FTPDIR%\%FTPFILE%
echo %user%>> %FTPDIR%\%FTPFILE%
echo %password%>> %FTPDIR%\%FTPFILE%
REM echo cd %FTPOUTDIR%>> %FTPDIR%\%FTPFILE%
REM FTPコピー処理
echo put %COPYDIR%\%SYSDATE%%OUTFILENAME1%>> %FTPDIR%\%FTPFILE%
echo put %COPYDIR%\%SYSDATE%%OUTFILENAME2%>> %FTPDIR%\%FTPFILE%
echo put %COPYDIR%\%SYSDATE%%OUTFILENAME3%>> %FTPDIR%\%FTPFILE%
REM 転送確認のためのファイルをコピー
echo get %SYSDATE%%OUTFILENAME1% %COPYDIR%\%SYSDATE%%OUTFILENAME1%OK>> %FTPDIR%\%FTPFILE%
echo get %SYSDATE%%OUTFILENAME2% %COPYDIR%\%SYSDATE%%OUTFILENAME2%OK>> %FTPDIR%\%FTPFILE%
echo get %SYSDATE%%OUTFILENAME3% %COPYDIR%\%SYSDATE%%OUTFILENAME3%OK>> %FTPDIR%\%FTPFILE%

REM FTPログアウト処理
echo bye>> %FTPDIR%\%FTPFILE%

ftp -s:%FTPDIR%\%FTPFILE%> %LOGDIR%\%ERROR_LOG%

REM ３つのファイルの転送が正常終了しているかの確認
IF not exist %COPYDIR%\%SYSDATE%%OUTFILENAME1%OK goto ERROR2
IF not exist %COPYDIR%\%SYSDATE%%OUTFILENAME2%OK goto ERROR2
IF not exist %COPYDIR%\%SYSDATE%%OUTFILENAME3%OK goto ERROR2

REM 送信したファイルを同ディレクトリにバックアップ（1日分のみ保持）
copy %COPYDIR%\%SYSDATE%%OUTFILENAME1% %COPYDIR%\BK_%OUTFILENAME1%
copy %COPYDIR%\%SYSDATE%%OUTFILENAME2% %COPYDIR%\BK_%OUTFILENAME2%
copy %COPYDIR%\%SYSDATE%%OUTFILENAME3% %COPYDIR%\BK_%OUTFILENAME3%

del %COPYDIR%\%SYSDATE%%OUTFILENAME1%OK
del %COPYDIR%\%SYSDATE%%OUTFILENAME2%OK
del %COPYDIR%\%SYSDATE%%OUTFILENAME3%OK

del %COPYDIR%\%SYSDATE%%OUTFILENAME1%
del %COPYDIR%\%SYSDATE%%OUTFILENAME2%
del %COPYDIR%\%OUTFILENAME3%
del %COPYDIR%\%SYSDATE%%OUTFILENAME3%
del %FTPDIR%\%FTPFILE%

REM FTPコピー正常終了時
REM ENDファイル作成
copy nul %COPYDIR%\%SYSDATE%.END
REM FTPログイン処理
echo open %server%> %FTPDIR%\%FTPFILE%
echo %user%>> %FTPDIR%\%FTPFILE%
echo %password%>> %FTPDIR%\%FTPFILE%
REM echo cd %FTPOUTDIR%>> %FTPDIR%\%FTPFILE%
REM FTPコピー処理
echo put %COPYDIR%\%SYSDATE%.END>> %FTPDIR%\%FTPFILE%
REM 転送確認のためのファイルをコピー
echo get %SYSDATE%.END %COPYDIR%\%SYSDATE%.ENDOK>> %FTPDIR%\%FTPFILE%
REM FTPログアウト処理
echo bye>> %FTPDIR%\%FTPFILE%

ftp -s:%FTPDIR%\%FTPFILE%>> %LOGDIR%\%ERROR_LOG%

REM ４つ目のファイルの転送が正常終了しているかの確認
IF not exist %COPYDIR%\%SYSDATE%.ENDOK goto ERROR2

del %COPYDIR%\%SYSDATE%.ENDOK

del %COPYDIR%\%SYSDATE%.END
del %FTPDIR%\%FTPFILE%

REM 正常終了時のログ出力
echo OK>%LOGDIR%\%ERROR_LOG%

exit 0

:ERROR1
REM 異常終了時のログ出力（FTPでのファイルコピーに失敗した場合）
echo FILE NOT FOUND>%LOGDIR%\%ERROR_LOG%
exit 1

:ERROR2
REM 異常終了時のログ出力（FTPでのファイルコピーに失敗した場合）
echo FtpFileCopy Failed>>%LOGDIR%\%ERROR_LOG%
exit 2

