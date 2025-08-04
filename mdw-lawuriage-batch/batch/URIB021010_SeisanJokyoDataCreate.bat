REM @echo off
REM ============================================================================
REM タイトル：精算状況データ作成処理
REM 説明	：精算業務の状況を管理する「店別精算状況データ」テーブルに初期値を作成する。
REM version 1.00 2009/03/03 L.Cheng:新規
REM ============================================================================

SET BATCHF_HOME=@filter.shellscript.baseDirectory.value@

CALL %BATCHF_HOME%\batch\SetBatchPath.bat

REM ===== 精算状況データ作成処理
%EXECJAVA% jp.co.vinculumjapan.swc.commons.dao.BatchController URIB021010
IF %ERRORLEVEL% NEQ 0 goto error

:end

CD %BATCHF_HOME%

exit 0

:error

exit 1