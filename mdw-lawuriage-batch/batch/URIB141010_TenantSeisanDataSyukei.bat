REM @echo off
REM ============================================================================
REM タイトル	：テナント精算データ集計処理
REM 説明	：買掛管理用に、店別テナント精算データの入力差分データを作成する処理。
REM version 1.00 2009/03/03 L.Cheng:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

REM ===== テナント精算データ集計処理
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB141010
IF %ERRORLEVEL% NEQ 0 goto error

:end

CD %BATCHF_HOME%

exit 0

:error

exit 1
