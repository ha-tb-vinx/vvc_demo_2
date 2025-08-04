REM @echo off
REM ============================================================================
REM タイトル：KSPSPFTPデータコピー処理
REM 説明	：KSPSP転送用のファイルをコピーします。
REM version 1.00 2011/02/03 Umemoto:新規 
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

REM コピー元ファイルディレクトリ
set COPYDIR="E:\mdware\datas\ftpdata\LocalUser\noauth\uriage\NCR"
REM コピー元ファイル名1
set INFILENAME1=SDPT.ftp
REM コピー元ファイル名2
set INFILENAME2=SMPU.ftp
REM 出力ファイル名1
set OUTFILENAME1=KDPT.STR
REM 出力ファイル名2
set OUTFILENAME2=KMPU.STR
REM システム日付（スラッシュなし）
set SYSDATE=%DATE:~0,4%%DATE:~5,2%%DATE:~8,2%
REM エラーログディレクトリ
set LOGDIR=%BATCHF_HOME%\log
REM エラーログ
set ERROR_LOG=FTP_ERROR_KSPSP.log
REM 終了情報
set ExitInf=OK

REM ファイルの存在チェック1
IF not exist %COPYDIR%\%INFILENAME1% (

REM 1つ目のファイルが存在しない場合
copy nul %COPYDIR%\%SYSDATE%%OUTFILENAME1%
set ExitInf=%INFILENAME1%_FILE_NOT_FOUND

) ELSE (

REM ファイルのコピー実行
copy %COPYDIR%\%INFILENAME1% %COPYDIR%\%SYSDATE%%OUTFILENAME1%

)

REM ファイルの存在チェック2
IF not exist %COPYDIR%\%INFILENAME2% (

REM 2つ目のファイルが存在しない場合
copy nul %COPYDIR%\%SYSDATE%%OUTFILENAME2%
set ExitInf=%INFILENAME2%_FILE_NOT_FOUND

) ELSE (

copy %COPYDIR%\%INFILENAME2% %COPYDIR%\%SYSDATE%%OUTFILENAME2%

)

REM ログ出力
echo %ExitInf%>%LOGDIR%\%ERROR_LOG%

exit 0

