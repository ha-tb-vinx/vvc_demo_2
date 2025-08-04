REM @echo off
REM ============================================================================
REM タイトル	：時間帯データ営業日付加
REM 説明	：POSの時間帯データの各レコード末尾に、ファイル名から算出した営業日を付加する
REM version 1.00 2016/05/23 T.Kamei:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

REM ===== 時間帯データ営業日付加
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB013420
IF %ERRORLEVEL% NEQ 0 goto error

:end

CD %BATCHF_HOME%

exit 0

:error

exit 1
