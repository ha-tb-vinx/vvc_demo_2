REM @echo off
REM ============================================================================
REM タイトル	：マスタ情報取得処理（レジ別取引精算Erec）
REM 説明	：マスタ情報取得処理（レジ別取引精算Erec）を実行する。
REM version 1.00 2017/04/27 J.Endo:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

REM ===== マスタ情報取得処理（レジ別取引精算Erec）
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB012402
IF %ERRORLEVEL% NEQ 0 goto error

:end

CD %BATCHF_HOME%

exit 0

:error

exit 1
