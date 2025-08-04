REM @echo off
REM ============================================================================
REM タイトル：KSPSPFTPデータコピー処理(リカバリ)
REM 説明	：KSPSP転送用のファイルをコピーします。
REM version 1.00 2011/02/10 S.Umemoto:新規 
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

REM ファイルの存在チェック1
IF not exist %COPYDIR%\%INFILENAME1% (

REM 1つ目のファイルが存在しない場合
goto ERROR

) ELSE (

REM ファイルのコピー実行
REM リカバリ用の設定　⇒　MM+通常名称
copy %COPYDIR%\%INFILENAME1% %COPYDIR%\MM%SYSDATE%%OUTFILENAME1%

)

REM ファイルの存在チェック2
IF not exist %COPYDIR%\%INFILENAME2% (

REM 2つ目のファイルが存在しない場合
goto ERROR

) ELSE (

REM ファイルのコピー実行
REM リカバリ用の設定　⇒　MM+通常名称
copy %COPYDIR%\%INFILENAME2% %COPYDIR%\MM%SYSDATE%%OUTFILENAME2%

)

exit 0

:ERROR

exit 1
